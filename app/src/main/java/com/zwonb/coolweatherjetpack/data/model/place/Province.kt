package com.zwonb.coolweatherjetpack.data.model.place

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

/**
 * 省份数据类
 *
 * @author zwonb
 * @date 2019-10-31
 */
class Province(
    @SerializedName("name") val provinceName: String,
    @SerializedName("id") val provinceCode: Int
) : LitePalSupport() {
    @Transient
    val id: Int = 0
}