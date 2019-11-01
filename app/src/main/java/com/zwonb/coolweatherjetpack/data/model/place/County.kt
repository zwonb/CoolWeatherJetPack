package com.zwonb.coolweatherjetpack.data.model.place

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

/**
 * @author zwonb
 * @date 2019-10-31
 */
class County(
    @SerializedName("name") val countyName: String,
    @SerializedName("weather_id") val weatherId: String
) : LitePalSupport() {
    @Transient
    val id = 0
    val cityId = 0
}