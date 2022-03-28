package com.example.topupmama.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.util.ObjectsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topupmama.R
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.ForeCastDb
import com.example.topupmama.data.local.entities.ForeCastDb_
import com.example.topupmama.data.models.Days
import com.example.topupmama.databinding.WeatheritemBinding
import com.example.topupmama.ui.DetailsActivity
import com.example.topupmama.utils.getDrawable
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter(private val item: (Country) -> Unit?) :
    ListAdapter<Country, WeatherAdapter.WeatherVh>(diffUtil) {


    private var unfilteredlist = mutableListOf<Country>()
    private var forecastDays = mutableListOf<String>()
    private var temps = mutableMapOf<String, List<String>>()


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
        temps[unfilteredlist[position].cityName]
        val forecasts = CountriesBox.store.boxFor(ForeCastDb::class.java)
            .query(ForeCastDb_.cityName.equal(unfilteredlist[position].cityName)).build()
            .findFirst()
        if (forecasts == null || forecasts.temps.isEmpty()) {
             return
        }
        else {

              if (forecasts.temps.size == 3){
                  holder.binding.firstDay.text = forecasts.temps[0] + "°"
                  holder.binding.secondDay.text = forecasts.temps[1] + "°"
                  holder.binding.thirdDay.text = forecasts.temps[2] + "°"
              }


            //holder.binding.firstDay.text = forecasts.temps.get(0)
        }
        holder.bind(unfilteredlist[position])
    }



    fun setData(list: List<Country>) {
        this.unfilteredlist.clear()
        this.unfilteredlist = list as MutableList<Country>
        submitList(list)
        notifyDataSetChanged()
    }


    fun setForeCastDays(days: String) {
        this.forecastDays.add(days)
        notifyItemInserted(forecastDays.size - 1)

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




    inner class WeatherVh(val binding: WeatheritemBinding) : RecyclerView.ViewHolder(binding.root) {


        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(country: Country?) {
            binding.cityName.text = country?.cityName

            if (country != null){
                binding.starImage.visibility = if (country.isFavorite) View.VISIBLE else View.GONE
            }

            if (country != null){
                binding.weatherIcon.visibility = View.VISIBLE
                if(country.description != null){
                    binding.weatherIcon.setImageResource(country.getDrawable(country.description))
                }

            }

            if (country?.temp == null) {
                binding.currentTemp.text = "..."
            } else {
                binding.currentTemp.text = country.temp.toString() + "°"
            }
            binding.asAt.text = country?.updatedAt?.takeLast(5) ?: "..."
            binding.tempDescription.text = country?.description ?: "..."

            if (forecastDays.isNotEmpty()) {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                binding.DayAfterTommorrow.text = formatter.parse(forecastDays[2]).toString().take(3)
                binding.Tommorrow.text = formatter.parse(forecastDays[1]).toString().take(3)
                binding.DayNow.text = formatter.parse(forecastDays[0]).toString().take(3)


            }


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