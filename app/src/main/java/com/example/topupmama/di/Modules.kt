package com.example.topupmama.di

import androidx.room.Room
import com.example.topupmama.BuildConfig
import com.example.topupmama.base.BaseApplication
import com.example.topupmama.data.local.AppDatabase
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.network.WeatherApi
import com.example.topupmama.ui.DetailViewModel
import com.example.topupmama.ui.WeatherViewModel
import com.example.topupmama.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/*
    *
    *  Network Module
    *
 */
val networkingModule: Module = module {

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.BUILD_TYPE) {
            "release" -> HttpLoggingInterceptor.Level.NONE
            else -> HttpLoggingInterceptor.Level.BODY
        }





        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single {

        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }
}

/*
    *
    *   Dao Module
    *
 */
private val daoModule: Module = module {
    single { get<AppDatabase>().weatherDao() }

}

/*
    *
    *  Room Database Module
    *
 */

val databaseModule: Module = module{

        single {
           Room.databaseBuilder(
                androidContext(),
                 AppDatabase::class.java,
                "TopUpMama"
            ).createFromAsset("database/countries.db").build()
        }

}
/*
    *
    *  Api Module
    *
 */
val apiModule: Module = module {
    single<WeatherApi> { get<Retrofit>().create() }
}

/*
    *
    *  Repository Module
    *
 */

val repositoryModule: Module = module {
    single { WeatherRepository(weatherApi = get(),weatherDao = get()) }
}

/*
    *
    *  ViewModel Module
    *
 */

val viewModelModule: Module = module {
    single { WeatherViewModel(get()) }
}
val detailViewModel: Module = module {
    single { DetailViewModel(get()) }
}
val baseApplication: Module = module {
    single { BaseApplication(get()) }
}
val worker: Module = module {
    single { BaseApplication(get()) }
}



val appModules: List<Module> = listOf(
    networkingModule,
    databaseModule,
    daoModule,
    apiModule,
    repositoryModule,
    viewModelModule,
    detailViewModel,
    baseApplication
)