package com.example.topupmama.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.topupmama.R
import com.example.topupmama.base.BaseActivity
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.FavoriteCountry
import com.example.topupmama.data.local.entities.ForeCastDb
import com.example.topupmama.data.local.entities.ForeCastDb_
import com.example.topupmama.databinding.ActivityDetailsBinding
import com.example.topupmama.utils.getDrawable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {


    private lateinit var country: Country
    private val viewModel by viewModel<DetailViewModel>()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        country = intent.getParcelableExtra("COUNTRY")!!
        binding.country = country


        binding.imageIcon.setImageResource(
         country.getDrawable(country.description!!)
        )

        val city = CountriesBox.store.boxFor(ForeCastDb::class.java)
            .query(ForeCastDb_.cityName.equal(country.cityName)).build().findFirst()


        val temperatureMap = mapOf(
            today to city!!.temps[firstDay].toFloat(),
            tommorrow to city.temps[secondDay].toFloat(),
            dayAfterTommorrow to city.temps[thirdDay].toFloat(),

            )

       if (!country.isFavorite){

       }
        binding.weatherReportGraph.labels =
            temperatureMap.map { entry -> entry.key }
        binding.weatherReportGraph.values = temperatureMap.values.toMutableList()
        binding.weatherReportGraph.endPointLabel = "Temp(°C)"



        binding.gone = country.icon.isNullOrEmpty()

        binding.addToFav.setOnClickListener {

            viewModel.update(true, -1, country.cityName)
            val favoriteCountry =
                FavoriteCountry(countryName = country.countryName, cityName = country.cityName)
            CountriesBox.store.boxFor(FavoriteCountry::class.java).put(favoriteCountry)

        }


    }

    companion object {

        const val today = "TODAY"
        const val tommorrow = "TOMMOROW"
        const val dayAfterTommorrow = "DAYAFTERTOMMORROW"
        const val firstDay = 0
        const val secondDay = 1
        const val thirdDay = 2

        fun startDetailsActivity(context: Context, country: Country) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("COUNTRY", country)
            startActivity(context, intent, null)


        }
    }


    override fun onResume() {
        super.onResume()
        binding.weatherReportGraph.invalidate()
    }


    override val layoutResId: Int
        get() = R.layout.activity_details


}