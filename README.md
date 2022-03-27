# WeatherApp
Writing Weather App  Using Android Architecture Components in 100% Kotlin.

**Background**

Build a weather app using Kotlin (or java) on android using weather data from public weather APIs.
   - A screen displaying a list of current temperatures from 20 cities 
   - A search bar for ﬁltering these cities
   - Items from the list can be able to be made a favorite, favourite cities should go to the top of the list
   - Clicking on the items should open an item screen displaying more detailed info of weather in that city 
   - Anotiﬁcation showing the current conditions of a favorite city at the top of the hour (when the app is not in the foreground) 
   - Please ensure the app works gracefully in and out of internet activity 
   - Write unit tests to validate that the app works well
   - Please submit an apk with a link to the codebase on GitHub

**Screenshots**

Cities screen | Details  screen
--- | --- |
<img src="https://github.com/Carlosokumu/TopUpMama/blob/master/previews/Screenshot_20220327-141639.png" width="280"/> | <img src="https://github.com/Carlosokumu/TopUpMama/blob/master/previews/Screenshot_20220327-140805.png" width="280"/> 


* Technologies used
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
    * [KOIN](https://insert-koin.io/)
    * [Retrofit](https://square.github.io/retrofit/)
    * [WorkManager](https://developer.android.com/jetpack/androidx/releases/work)
    * [Jetpack](https://developer.android.com/jetpack)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Timber](https://github.com/JakeWharton/timber)
    * [Room](https://developer.android.com/training/data-storage/room)
    * [ObjectBox](https://docs.objectbox.io/getting-started)


* Architecture
    * MVVM - Model View View Model

* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
    
 * Tests
    * [JUnit5](https://junit.org/junit5/)
    * [Spek](https://www.spekframework.org/)
    * [MockK](https://github.com/mockk/mockk)
    * [Turbine](https://github.com/cashapp/turbine)
    
 **Unit Testing**
  Unit testing is done on the daos,room,repository and Api.
  More tests will be added


