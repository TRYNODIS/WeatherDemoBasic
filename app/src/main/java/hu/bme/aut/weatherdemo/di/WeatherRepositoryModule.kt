package hu.bme.aut.weatherdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.weatherdemo.model.WeatherNetworkDataSource
import hu.bme.aut.weatherdemo.network.WeatherAPI
import hu.bme.aut.weatherdemo.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherNetworkDataSource(weatherAPI: WeatherAPI): WeatherNetworkDataSource {
        return WeatherNetworkDataSource(weatherAPI)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherNetworkDataSource: WeatherNetworkDataSource
    ): WeatherRepository {
        return WeatherRepository(weatherNetworkDataSource)
    }
}