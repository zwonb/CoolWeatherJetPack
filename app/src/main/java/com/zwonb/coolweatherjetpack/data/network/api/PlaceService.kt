package com.zwonb.coolweatherjetpack.data.network.api

import com.zwonb.coolweatherjetpack.data.model.place.City
import com.zwonb.coolweatherjetpack.data.model.place.County
import com.zwonb.coolweatherjetpack.data.model.place.Province
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author zwonb
 * @date 2019-10-31
 */
interface PlaceService {

    @GET("api/china")
    fun getProvinces(): Call<MutableList<Province>>

    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Call<MutableList<City>>

    @GET("api/china/{provinceId}/{cityId}")
    fun getCounties(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Call<MutableList<County>>
}