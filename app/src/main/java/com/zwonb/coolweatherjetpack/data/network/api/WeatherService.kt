package com.zwonb.coolweatherjetpack.data.network.api

import com.zwonb.coolweatherjetpack.data.model.weather.HeWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author zwonb
 * @date 2019-10-31
 */
interface WeatherService {

    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String): Call<HeWeather>

    @GET("api/bing_pic")
    fun getBingPic(): Call<String>
}