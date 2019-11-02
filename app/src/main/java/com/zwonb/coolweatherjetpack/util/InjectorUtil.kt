package com.zwonb.coolweatherjetpack.util

import com.zwonb.coolweatherjetpack.data.db.CoolWeatherDatabase
import com.zwonb.coolweatherjetpack.data.repo.PlaceRepository
import com.zwonb.coolweatherjetpack.data.repo.WeatherRepository
import com.zwonb.coolweatherjetpack.ui.MainModelFactory
import com.zwonb.coolweatherjetpack.ui.area.ChooseAreaModelFactory
import com.zwonb.coolweatherjetpack.ui.weather.WeatherModelFactory

/**
 * @author zwonb
 * @date 2019-11-01
 */
object InjectorUtil {

    private fun getPlaceRepository() =
        PlaceRepository.getInstance(CoolWeatherDatabase.getPlaceDao())

    private fun getWeatherRepository() =
        WeatherRepository.getInstance(CoolWeatherDatabase.getWeatherDao())

    fun getChooseAreaModelFactory() = ChooseAreaModelFactory(getPlaceRepository())

    fun getWeatherModelFactory() = WeatherModelFactory(getWeatherRepository())

    fun getMainModelFactory() = MainModelFactory(getWeatherRepository())

}