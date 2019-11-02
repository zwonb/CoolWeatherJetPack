package com.zwonb.coolweatherjetpack.ui.weather

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zwonb.coolweatherjetpack.CoolWeatherApplication
import com.zwonb.coolweatherjetpack.data.model.weather.Weather
import com.zwonb.coolweatherjetpack.data.repo.WeatherRepository
import kotlinx.coroutines.launch

/**
 * @author zwonb
 * @date 2019-11-02
 */
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var weather = MutableLiveData<Weather>()
    var bingPicUrl = MutableLiveData<String>()
    var refreshing = MutableLiveData<Boolean>()
    var weatherInitialized = MutableLiveData<Boolean>()
    var weatherId = ""

    fun getWeather() {
        launch({
            weather.value = repository.getWeather(weatherId)
            weatherInitialized.value = true
        }, {
            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
        getBingPic(false)
    }

    fun refreshWeather() {
        refreshing.value = true
        launch({
            weather.value = repository.refreshWeather(weatherId)
            refreshing.value = false
            weatherInitialized.value = true
        }, {
            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
            refreshing.value = false
        })
        getBingPic(true)
    }

    fun isWeatherCached() = repository.isWeatherCached()

    fun getWeatherCached() = repository.getCachedWeather()

    fun onRefresh() {
        refreshWeather()
    }

    private fun getBingPic(refresh: Boolean) {
        launch({
            bingPicUrl.value = if (refresh) {
                repository.refreshBingPic()
            } else {
                repository.getBingPic()
            }
        }, {
            Toast.makeText(CoolWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                error(e)
            }
        }
}