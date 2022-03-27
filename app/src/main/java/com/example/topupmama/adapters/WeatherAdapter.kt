package com.example.topupmama.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter(private val item: (Country) -> Unit?) :
    ListAdapter<Country, WeatherAdapter.WeatherVh>(diffUtil) {


    private var days = arrayListOf<Days>()
    private var unfilteredlist = mutableListOf<Country>()
    private var forecastDays = mutableListOf<String>()
    private var forecastTemps = mutableListOf<String>()
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
//       if (this.forecastTemps.isNotEmpty()){
//           //  holder.binding.firstDay.text = forecastTemps[0]
//          // holder.binding.secondDay.text = forecastTemps[1]
//          // holder.binding.thirdDay.text = forecastTemps[2]
//       }

        // Toast.makeText(holder.itemView.context,holder.itemViewType.toString(),Toast.LENGTH_SHORT).show()
        var map = temps[unfilteredlist[position].cityName]
        val waah = CountriesBox.store.boxFor(ForeCastDb::class.java)
            .query(ForeCastDb_.cityName.equal(unfilteredlist[position].cityName)).build()
            .findFirst()
        if (waah == null || waah.temps.isEmpty()) {
             return
        }
        else {
               Log.d("REALSIZE",waah?.temps?.size.toString())
              if (waah.temps.size == 3){
                  holder.binding.firstDay.text = waah.temps.get(0)
                  holder.binding.secondDay.text = waah.temps.get(1)
                  holder.binding.thirdDay.text = waah.temps?.get(2)
              }


            holder.binding.firstDay.text = waah.temps?.get(0)
            //holder.binding.secondDay.text = waah.temps?.get(1)
            //holder.binding.thirdDay.text = waah.temps?.get(2)
        }
        waah?.cityName?.let { Log.d("MRAS", it) }
        waah?.temps?.forEach {
            Log.d("FORMAN", it)
        }
//        if (map!!.isNotEmpty()){
//            Toast.makeText(holder.itemView.context,holder.itemViewType.toString(),Toast.LENGTH_SHORT).show()
//        }
        // Log.d("MASTERSIZE",map?.size.toString())

        holder.bind(unfilteredlist[position], days)
    }


    fun setMaps(map: Map<String, List<String>>) {
        temps = map as MutableMap<String, List<String>>
        notifyDataSetChanged()
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
        //notifyItemInserted(forecastDays.size - 1)

    }


//    fun setForecastTemps( temp: List<String>){
//        this.forecastTemps.addAll(temp)
//        notifyItemInserted(forecastTemps.size - 1)
//    }


    fun setForeCast(day: Days?) {
        if (day == null) {
            return
        }
        this.days.add(day)
        // notifyItemInserted(this.days.indexOf(day))
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


    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class WeatherVh(val binding: WeatheritemBinding) : RecyclerView.ViewHolder(binding.root) {


        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(country: Country, days: List<Days>) {
            binding.cityName.text = country.cityName
//            binding.weatherIcon.setImageResource(
//              country.getDrawable(country.description!!)
//            )
            binding.starImage.visibility = if (country.isFavorite) View.VISIBLE else View.GONE
            if (country.isFavorite) {
                binding.starImage.drawable.setTint(itemView.context.getColor(R.color.rock))
            }


            if (country.temp == null) {
                binding.currentTemp.text = "..."
            } else {
                binding.currentTemp.text = country.temp.toString() + "°"
            }
            binding.asAt.text = country.updatedAt?.takeLast(5) ?: "..."
            binding.tempDescription.text = country.description ?: "..."

            if (days.isNotEmpty()) {
                Log.d("NOTEMPTY", days[0].date)
            }

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