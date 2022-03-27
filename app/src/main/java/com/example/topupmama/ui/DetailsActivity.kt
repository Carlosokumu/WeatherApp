package com.example.topupmama.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.topupmama.R
import com.example.topupmama.base.BaseActivity
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.*
import com.example.topupmama.databinding.ActivityDetailsBinding
import com.example.topupmama.utils.getDrawable
import com.uk.tastytoasty.TastyToasty
import io.objectbox.Box
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {


    private lateinit var country: Country
    private val viewModel by viewModel<DetailViewModel>()
    private lateinit var favorites: Box<FavoriteCountry>

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

        favorites = CountriesBox.store.boxFor(FavoriteCountry::class.java)



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
        Toast.makeText(this,favorites.all.size.toString(),Toast.LENGTH_SHORT).show()
        binding.addToFav.setOnClickListener {
           val stagedCountry = favorites.query(FavoriteCountry_.cityName.equal(country.cityName)).build()
            .findFirst()
            if (stagedCountry != null){
                viewModel.updateFav(false, 0, country.cityName)
                favorites.remove(stagedCountry)
                TastyToasty.Builder(this)
                    .setText("Sucessfully removed ${country.cityName} from favorites")
                    .setBackgroundColor(R.color.transpar)
                    .showTail(true)
                    .show()
            }
            else {
                TastyToasty.Builder(this)
                    .setText("Sucessfully Added ${country.cityName} to favorites")
                    .setBackgroundColor(R.color.transpar)
                    .showTail(true)
                    .show()
                viewModel.updateFav(true, -1, country.cityName)
                val newFav = FavoriteCountry(countryName = country.countryName,cityName = country.cityName)
                favorites.put(newFav)
            }

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