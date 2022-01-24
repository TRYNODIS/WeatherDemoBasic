package hu.bme.aut.weatherdemo.ui.weather

import hu.bme.aut.weatherdemo.model.network.WeatherResult

sealed class WeatherViewState

object InProgress : WeatherViewState()
data class WeatherResponseSuccess(val data: WeatherResult) : WeatherViewState()
data class WeatherResponseFailure(val exceptionMsg: String) : WeatherViewState()