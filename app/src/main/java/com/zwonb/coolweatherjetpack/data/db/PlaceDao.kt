package com.zwonb.coolweatherjetpack.data.db

import com.zwonb.coolweatherjetpack.data.model.place.City
import com.zwonb.coolweatherjetpack.data.model.place.County
import com.zwonb.coolweatherjetpack.data.model.place.Province
import org.litepal.LitePal
import org.litepal.extension.findAll

/**
 * @author zwonb
 * @date 2019-10-31
 */
class PlaceDao {

    fun getProvinceList(): MutableList<Province> = LitePal.findAll<Province>()

    fun getCityList(provinceId: Int): MutableList<City> =
        LitePal.where("provinceId = ?", provinceId.toString()).find(City::class.java)

    fun getCountyList(cityId: Int): MutableList<County> =
        LitePal.where("cityId = ?", cityId.toString()).find(County::class.java)

    fun saveProvinceList(provinceList: List<Province>?) {
        if (!provinceList.isNullOrEmpty()) {
            LitePal.saveAll(provinceList)
        }
    }

    fun saveCityList(cityList: List<City>?) {
        if (!cityList.isNullOrEmpty()) {
            LitePal.saveAll(cityList)
        }
    }

    fun saveCountyList(countyList: List<County>?) {
        if (!countyList.isNullOrEmpty()) {
            LitePal.saveAll(countyList)
        }
    }
}