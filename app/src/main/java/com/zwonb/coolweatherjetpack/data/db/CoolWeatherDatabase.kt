package com.zwonb.coolweatherjetpack.data.db

/**
 * @author zwonb
 * @date 2019-10-31
 */
object CoolWeatherDatabase {

    private var placeDao: PlaceDao? = null
    private var weatherDao: WeatherDao? = null

    fun getPlaceDao(): PlaceDao {
        if (placeDao == null) {
            placeDao = PlaceDao()
        }
        return placeDao!!
    }

    fun getWeatherDao(): WeatherDao {
        if (weatherDao == null) weatherDao = WeatherDao()
        return weatherDao!!
    }
}