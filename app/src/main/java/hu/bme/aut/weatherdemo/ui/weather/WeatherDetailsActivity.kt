package hu.bme.aut.weatherdemo.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.databinding.ActivityWeatherDetailsBinding
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.city_row.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeatherDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_CITY = "KEY_CITY"
    }

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityWeatherDetailsBinding

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityName = intent.getStringExtra(KEY_CITY)!!

        binding.tvCity.text = cityName

        weatherViewModel.getWeatherLiveData().observe(this,
            { WeatherResult -> render(WeatherResult) })
    }

    private fun render(result: WeatherViewState) {
        when (result) {
            is InProgress -> {
                binding.progressLoad.visibility = View.VISIBLE
            }
            is WeatherResponseSuccess -> {
                binding.progressLoad.visibility = View.GONE

                this.responseIsSuccess(result.data)
            }
            is WeatherResponseFailure -> {
                binding.progressLoad.visibility = View.GONE

                binding.tvMain.text = result.exceptionMsg
            }
        }
    }

    override fun onResume() {
        super.onResume()

        weatherViewModel.getWeather(cityName)
    }

    private fun responseIsSuccess(weatherData: WeatherResult) {
        Glide.with(this@WeatherDetailsActivity)
            .load("https://openweathermap.org/img/w/${weatherData.weather?.get(0)?.icon}.png")
            .into(ivWeatherIcon)


        binding.tvMain.text = weatherData.weather?.get(0)?.main
        binding.tvDescription.text = weatherData.weather?.get(0)?.description
        binding.tvTemperature.text =
            getString(R.string.temperature, weatherData.main?.temp?.toFloat().toString())

        val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
        val sunriseDate = Date((weatherData.sys?.sunrise?.toLong())!! * 1000)
        val sunriseTime = sdf.format(sunriseDate)
        binding.tvSunrise.text = getString(R.string.sunrise, sunriseTime)
        val sunsetDate = Date(weatherData.sys.sunset?.toLong()!! * 1000)
        val sunsetTime = sdf.format(sunsetDate)
        binding.tvSunset.text = getString(R.string.sunset, sunsetTime)
    }
}