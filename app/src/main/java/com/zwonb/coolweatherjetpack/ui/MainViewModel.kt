package com.zwonb.coolweatherjetpack.ui

import androidx.lifecycle.ViewModel
import com.zwonb.coolweatherjetpack.data.repo.WeatherRepository

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun isWeatherCached() = repository.isWeatherCached()

}