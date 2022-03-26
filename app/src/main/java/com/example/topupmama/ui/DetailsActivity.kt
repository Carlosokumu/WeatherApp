package com.example.topupmama.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.topupmama.R
import com.example.topupmama.adapters.WeatherAdapter
import com.example.topupmama.base.BaseActivity
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.DummyData
import com.example.topupmama.databinding.ActivityDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {



    private lateinit var country: Country
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        country = intent.getParcelableExtra("COUNTRY")!!
        binding.country = country


        binding.imageIcon.setImageResource(
            when (country.cityName) {
                "Lagos" -> R.drawable.ic_day_thunderstorm
                "Abuja" -> R.drawable.ic_daycloudy
                else -> R.drawable.ic_rain
            }
        )


        val temperatureMap = mapOf(
            "Mar 14" to 36f,
            "Mar 15" to 35f,
            "Mar 16" to 35f,
            "Mar 17" to 33f,
            "Mar 18" to 36f
        )


        binding.weatherReportGraph.labels = temperatureMap.map { entry -> entry.key }
        binding.weatherReportGraph.values = temperatureMap.values.toMutableList()
        binding.weatherReportGraph.endPointLabel = "${temperatureMap.entries.last().value} °C"


        binding.gone = country.icon.isNullOrEmpty()

        binding.addToFav.setOnClickListener {

            viewModel.update(true, -1, country.cityName)

        }
         if (country.isFavorite){
             binding.addToFav.drawable.setTint(resources.getColor(R.color.rock))
         }
        else {
             binding.addToFav.drawable.setTint(resources.getColor(R.color.white_uni))
        }



    }

    companion object {


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