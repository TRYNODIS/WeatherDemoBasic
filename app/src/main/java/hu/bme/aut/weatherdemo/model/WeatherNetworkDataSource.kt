package hu.bme.aut.weatherdemo.model

import hu.bme.aut.weatherdemo.BuildConfig
import hu.bme.aut.weatherdemo.network.WeatherAPI
import hu.bme.aut.weatherdemo.util.NetworkErrorResult
import hu.bme.aut.weatherdemo.util.NetworkResponse
import hu.bme.aut.weatherdemo.util.NetworkResult
import java.lang.Exception

class WeatherNetworkDataSource(private val weatherAPI: WeatherAPI) {

    suspend fun getWeather(cityName: String): NetworkResponse<Any> {
        try {
            val response = weatherAPI.getWeatherData(
                cityName,
                "metric",
                BuildConfig.WEATHER_API_KEY
            )

            response?.let {
                return NetworkResult(it.body()!!)
            }

            return NetworkErrorResult(Exception("No data"))
        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }
}