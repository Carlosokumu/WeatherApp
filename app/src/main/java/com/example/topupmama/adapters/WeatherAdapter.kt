package com.example.topupmama.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.util.ObjectsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topupmama.R
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.databinding.WeatheritemBinding
import com.example.topupmama.ui.DetailsActivity
import com.example.topupmama.utils.getDrawable
import java.util.*


class WeatherAdapter(private val item: (Country) -> Unit?) :
    ListAdapter<Country, WeatherAdapter.WeatherVh>(diffUtil) {


    private val data = mutableListOf<Country>()
    private var unfilteredlist  = mutableListOf<Country>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherVh {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.weatheritem,
            parent,
            false
        ) as WeatheritemBinding


        return WeatherVh(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherVh, position: Int) {
         holder.itemView.setOnClickListener {
          DetailsActivity.startDetailsActivity(it.context, unfilteredlist[position])


         }





        holder.bind(unfilteredlist[position])
    }


    fun setData(list: List<Country>) {
        this.unfilteredlist.clear()
        this.unfilteredlist = list as MutableList<Country>
        submitList(list)
        notifyDataSetChanged()
    }


    fun replace(newCountry: Country) {
        for (country in unfilteredlist) {
            if (ObjectsCompat.equals(country.cityName, newCountry.cityName)) {
                val index: Int = unfilteredlist.indexOf(country)
                unfilteredlist[index] = newCountry
                notifyItemChanged(index)
                break
            }
        }
    }


    fun filter(query: CharSequence?) {
        val list = mutableListOf<Country>()

        if (!query.isNullOrEmpty()) {
            list.addAll(unfilteredlist.filter {
                it.cityName.toLowerCase(Locale.getDefault())
                    .contains(query.toString().toLowerCase(Locale.getDefault()))
            })
        } else {
            list.addAll(unfilteredlist)
        }

        submitList(list)
    }


    inner class WeatherVh(val binding: WeatheritemBinding) : RecyclerView.ViewHolder(binding.root){



        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        fun bind(country: Country){
            binding.cityName.text = country.cityName
//            binding.weatherIcon.setImageResource(
//              country.getDrawable(country.description!!)
//            )
            binding.starImage.visibility = if (country.isFavorite) View.VISIBLE else View.GONE
            if (country.isFavorite){
                binding.starImage.drawable.setTint(itemView.context.getColor(R.color.rock))
            }


            if (country.temp == null){
                binding.currentTemp.text ="..."
            }
            else {
                binding.currentTemp.text = country.temp.toString() + "°"
            }
            binding.asAt.text = country.updatedAt ?: "..."
            binding.tempDescription.text = country.description ?: "..."



        }


    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Country>() {

            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.cityName == newItem.cityName

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem
        }


    }


}