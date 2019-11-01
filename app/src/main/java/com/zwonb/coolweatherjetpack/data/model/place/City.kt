package com.zwonb.coolweatherjetpack.data.model.place

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

/**
 * @author zwonb
 * @date 2019-10-31
 */
class City(
    @SerializedName("name") val cityName: String,
    @SerializedName("id") val cityCode: Int
) : LitePalSupport() {
    @Transient
    val id = 0
    var provinceId = 0
}