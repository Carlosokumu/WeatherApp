package com.example.topupmama

import java.util.concurrent.TimeUnit
import com.example.topupmama.data.models.WeatherResponse
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.network.WeatherApi
import com.example.topupmama.network.WeatherResult
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*

     Weather Repository Tests
 */

class WeatherRepositoryTest: Spek({

    // Mock Web Server and Network API
    lateinit var mockWebServer: MockWebServer
    lateinit var okHttpClient: OkHttpClient
    lateinit var loggingInterceptor: HttpLoggingInterceptor
    var weatherapi: WeatherApi
    lateinit var weatherRepository: WeatherRepository

    lateinit var result:  WeatherResult<WeatherResponse>

    fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    Feature("Fetching Weather from Api") {
        beforeEachTest {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = MockRequestDispatcher()
            mockWebServer.start()
            loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClient = buildOkhttpClient(loggingInterceptor)

            val gson = GsonBuilder()
                .serializeNulls()
                .create()

              weatherapi = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(WeatherApi::class.java)

            weatherRepository =
                WeatherRepository(weatherapi, weatherDao = mockk())
        }
        afterEachTest {

        }
        Scenario("Fetching the weather information from the open Weather") {

            Given("Make the actual API call to get the result") {
                runBlocking {
                    result = weatherRepository.fetchCurrentWeather("Lagos")
                }
            }

            When("We assert that the result we get is an instance of  WeatherResult") {
                Truth.assertThat(result).isInstanceOf(WeatherResult.Success::class.java)
            }

            Then("We check that the name of the city is the same") {
                Truth.assertThat((result as WeatherResult.Success).data.location.name)
                    .isEqualTo("Lagos")
            }

        }
    }


})