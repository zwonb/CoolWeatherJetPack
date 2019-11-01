package com.zwonb.coolweatherjetpack.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @author zwonb
 * @date 2019-10-31
 */
object ServiceCreator {

    private const val BASE_URL = "http://guolin.tech/"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit: Retrofit = builder.build()

//    fun <T> create(clazz: Class<T>) = retrofit.create(clazz)
}

inline fun <reified T> create() :T = ServiceCreator.retrofit.create(T::class.java)