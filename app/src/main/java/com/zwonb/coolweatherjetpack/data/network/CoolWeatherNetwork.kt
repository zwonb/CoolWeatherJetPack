package com.zwonb.coolweatherjetpack.data.network

import com.zwonb.coolweatherjetpack.data.network.api.PlaceService
import retrofit2.create

/**
 * 说明
 *
 * @author zwonb
 * @date 2019-10-31
 */
class CoolWeatherNetwork {

    private val placeService = create<PlaceService>()

}