package hu.bme.aut.weatherdemo.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.repository.WeatherRepository
import hu.bme.aut.weatherdemo.util.NetworkErrorResult
import hu.bme.aut.weatherdemo.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val result = MutableLiveData<WeatherViewState>()
    fun getWeatherLiveData() = result

    fun getWeather(cityName: String) {
        result.value = InProgress

        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getWeather(cityName)
            when (response) {
                is NetworkResult -> {
                    val weatherResult = response.result as WeatherResult

                    result.postValue(WeatherResponseSuccess(weatherResult))
                }
                is NetworkErrorResult -> {
                    result.postValue(WeatherResponseFailure(response.errorMessage.message!!))
                }
            }
        }
    }
}