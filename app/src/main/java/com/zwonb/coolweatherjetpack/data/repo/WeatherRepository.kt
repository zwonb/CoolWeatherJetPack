package com.zwonb.coolweatherjetpack.data.repo

import com.zwonb.coolweatherjetpack.data.db.WeatherDao
import com.zwonb.coolweatherjetpack.data.model.weather.Weather
import com.zwonb.coolweatherjetpack.data.network.api.WeatherService
import com.zwonb.coolweatherjetpack.data.network.create
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

/**
 * @author zwonb
 * @date 2019-11-01
 */
class WeatherRepository(private val weatherDao: WeatherDao) {

    companion object {

        private lateinit var instance: WeatherRepository

        fun getInstance(weatherDao: WeatherDao): WeatherRepository {
            if (!::instance.isInitialized) {
                synchronized(WeatherRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = WeatherRepository(weatherDao)
                    }
                }
            }
            return instance
        }
    }

    suspend fun getWeather(weatherId: String): Weather {
        val weather = weatherDao.getCachedWeatherInfo()
        return weather ?: requestWeather(weatherId)
    }

    suspend fun refreshWeather(weatherId: String) = requestWeather(weatherId)

    suspend fun getBingPic(): String {
        val url = weatherDao.getCachedBingPic()
        return url ?: requestBingPic()
    }

    suspend fun refreshBingPic() = requestBingPic()

    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()!!

    private suspend fun requestBingPic(): String = withContext(Dispatchers.IO) {
        val url = create<WeatherService>().getBingPic().await()
        weatherDao.cacheBingPic(url)
        url
    }

    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = create<WeatherService>().getWeather(weatherId).await()
        val weather = heWeather.weather!![0]
        weatherDao.cacheWeatherInfo(weather)
        weather
    }


}