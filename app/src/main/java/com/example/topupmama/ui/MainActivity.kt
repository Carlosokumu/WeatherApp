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
import androidx.lifecycle.lifecycleScope
import com.example.topupmama.R
import com.example.topupmama.adapters.WeatherAdapter
import com.example.topupmama.base.BaseActivity
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.WeatherState
import com.example.topupmama.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {


   private val viewModel by viewModel<WeatherViewModel>()
   private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchItem: MenuItem
    private val  currentCountries = mutableListOf<Country>()

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
            }
        }

        //weatherAdapter.setData(Constants.getDummyData())
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

        Toast.makeText(this,"oncreate Called",Toast.LENGTH_SHORT).show()




        lifecycleScope.launchWhenStarted {

            viewModel.mutableWeatherState.collect { weatherState ->
                when(weatherState){
                    is WeatherState.Result -> {
                        val country = weatherState.weather

                        //Insert a country into the database
                        viewModel.insertCountry(country)
                        weatherAdapter.replace(country)


                        Toast.makeText(
                            this@MainActivity,
                            country.cityName.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is WeatherState.Loading -> {
                        binding.swipeRefresh.isRefreshing = true
                        Toast.makeText(this@MainActivity, "lOADING", Toast.LENGTH_SHORT).show()
                    }
                    is WeatherState.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(this@MainActivity, weatherState.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

       // weatherAdapter.moveToPosition(Constants.getDummyData()[2])



      //Constants.getDummyData().stream().toArray<DummyData>(DummyData::class)
       // Iterable.toArray(locations, WorldLocation::class.java))
        val strs: MutableList<String> = ArrayList()
        strs.add("hello")
        strs.add("world")

        test(*strs.toTypedArray())
        viewModel.countriesModel.observe(this,::onCountriesFetched)
       // Log.d("HERE",*strs.toTypedArray())
    }




    @ExperimentalCoroutinesApi
    private fun onCountriesFetched(countries: List<Country>?) {
         Toast.makeText(this,countries?.size.toString(),Toast.LENGTH_SHORT).show()
         weatherAdapter.setData(countries!!)
         loadWeather(*countries.toTypedArray())
         currentCountries.clear()
         currentCountries.addAll(countries)
         for (i in  countries){
             Log.d("ISFAVORITE",i.isFavorite.toString())
         }

    }


    private fun test(vararg strings: String){
      Log.d("FORMATTED", strings.toString())
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
        Toast.makeText(this,"OnResume Called",Toast.LENGTH_SHORT).show()
        viewModel.fetchAllCountries()
    }



}