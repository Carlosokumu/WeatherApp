package com.example.topupmama

import app.cash.turbine.test
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.WeatherResponse
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.network.WeatherResult
import com.example.topupmama.ui.WeatherViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@ExperimentalTime
class WeatherViewModelTest : Spek({
    val  weatherRepository = mockk<WeatherRepository>()
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var country:  Country
    val weatherResponse = mockk<WeatherResponse>()

    val dispatcher = TestCoroutineDispatcher()


    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }


    group("Fetching Weather Information"){
        beforeEachTest {
            weatherViewModel =
                WeatherViewModel(weatherRepository = weatherRepository)
           // country = Country()
        }
    }

    test("Assert that an event was received and return it") {

        runBlocking {
            coEvery { weatherRepository.fetchCurrentWeather("Lagos") } returns WeatherResult.Success(
                data = weatherResponse
            )
            //weatherViewModel.fetchCurrentWeather(country)
            coVerify { weatherRepository.fetchCurrentWeather("Lagos") }
            weatherViewModel.mutableWeatherState.test {
                awaitEvent()
            }
        }
    }



}

)
