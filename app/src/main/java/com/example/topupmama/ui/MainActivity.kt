package com.example.topupmama.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.util.ObjectsCompat
import androidx.lifecycle.lifecycleScope
import com.example.topupmama.R
import com.example.topupmama.adapters.WeatherAdapter
import com.example.topupmama.base.BaseActivity
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.ForeCastDb
import com.example.topupmama.data.local.entities.ForeCastDb_
import com.example.topupmama.data.models.Days
import com.example.topupmama.data.models.DummyData
import com.example.topupmama.data.models.ForeCastState
import com.example.topupmama.data.models.WeatherState
import com.example.topupmama.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {


   private val viewModel by viewModel<WeatherViewModel>()
   private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchItem: MenuItem
    private val  currentCountries = mutableListOf<Country>()
    private var increment : Int = 0
    private var forecastDays = mutableListOf<String>()
    private var tempMap : Map<String,List<String>> = mapOf()

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        weatherAdapter = WeatherAdapter {
           // val intent = Intent(this, DetailsActivity::class.java)
             // intent.putExtra("DUMMY", it)
             // startActivity(intent)
        }
        binding.swipeRefresh.setColorSchemeColors(Color.RED)
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(Color.TRANSPARENT)
        binding.swipeRefresh.setOnRefreshListener {
            if (!currentCountries.isNullOrEmpty()){
                loadWeather(*currentCountries.toTypedArray())
                loadForeCasts(*currentCountries.toTypedArray())
            }
        }


        binding.adapter = weatherAdapter
        binding.toolbar.inflateMenu(R.menu.search_menu)
        searchItem = binding.toolbar.menu.findItem(R.id.search_item)
        searchView = searchItem.actionView as SearchView




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                weatherAdapter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }
        })






        lifecycleScope.launchWhenStarted {

            viewModel.mutableWeatherState.collect { weatherState ->
                when(weatherState){
                    is WeatherState.Result -> {
                        val country = weatherState.weather

                        //Insert a country into the database
                        viewModel.insertCountry(country)
                        weatherAdapter.replace(country)
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is WeatherState.Loading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is WeatherState.Error -> {
                        binding.swipeRefresh.isRefreshing = false

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {

            viewModel.forecastState.collect {  forecaststate ->
                when(forecaststate){
                    is  ForeCastState.Result -> {
                        val collector = mutableListOf<String>()
                        for(i in forecaststate.weather.forecast.forecastday){
                            collector.add(i.day.avgtemp_c.toString())
                        }
                        var special = ForeCastDb(cityName = forecaststate.weather.location.name,temps = collector)
                        CountriesBox.store.boxFor(ForeCastDb::class.java).put(special)


                        for (i in forecaststate.weather.forecast.forecastday){
                            weatherAdapter.setForeCastDays(i.date)
                        }


                       // weatherAdapter.setForeCast(forecaststate.weather.forecast.forecastday[1])

                    }
                    is  ForeCastState.Loading -> {
                        binding.swipeRefresh.isRefreshing = true

                    }
                    is  ForeCastState.Error -> {

                    }
                }
            }
        }








        viewModel.countriesModel.observe(this,::onCountriesFetched)

    }




    @ExperimentalCoroutinesApi
    private fun onCountriesFetched(countries: List<Country>?) {
         weatherAdapter.setData(countries!!)
         loadWeather(*countries.toTypedArray())
         loadForeCasts(*countries.toTypedArray())
         currentCountries.clear()
         currentCountries.addAll(countries)


    }






    override val layoutResId: Int
        get() = R.layout.activity_main


    @ExperimentalCoroutinesApi
    private fun loadWeather(vararg countries: Country){

        for (i in countries){
            viewModel.fetchCurrentWeather(i)
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.fetchAllCountries()
    }



    @ExperimentalCoroutinesApi
    private fun loadForeCasts(vararg countries: Country){
        for (i in countries){
            viewModel.fetchForeCastWeather(i.cityName,days = 5)
        }
    }


    fun test(cityName: String){
        viewModel.fetchForeCastWeather(cityName,days = 5)
    }



}