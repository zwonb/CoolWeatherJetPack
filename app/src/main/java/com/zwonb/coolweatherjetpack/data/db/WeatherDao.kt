package com.zwonb.coolweatherjetpack.data.db

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.zwonb.coolweatherjetpack.CoolWeatherApplication
import com.zwonb.coolweatherjetpack.data.model.weather.Weather

/**
 * @author zwonb
 * @date 2019-10-31
 */
class WeatherDao {

    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        CoolWeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)
            .edit {
                val weatherInfo = Gson().toJson(weather)
                putString("weather", weatherInfo)
            }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo =
            CoolWeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)
                .getString("weather", null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        CoolWeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)
            .edit {
                putString("bing_pic", bingPic)
            }
    }

    fun getCachedBingPic(): String? =
        CoolWeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)
            .getString("bing_pic", null)

}