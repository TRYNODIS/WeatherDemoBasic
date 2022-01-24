package hu.bme.aut.weatherdemo.repository

import hu.bme.aut.weatherdemo.model.WeatherNetworkDataSource
import hu.bme.aut.weatherdemo.util.NetworkResponse

class WeatherRepository(private val weatherNetworkDataSource: WeatherNetworkDataSource) {
    suspend fun getWeather(cityName: String): NetworkResponse<Any> {
        return weatherNetworkDataSource.getWeather(cityName)
    }
}