package com.zwonb.coolweatherjetpack.data.repo

import com.zwonb.coolweatherjetpack.data.db.PlaceDao
import com.zwonb.coolweatherjetpack.data.network.api.PlaceService
import com.zwonb.coolweatherjetpack.data.network.create
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

/**
 * @author zwonb
 * @date 2019-10-31
 */
class PlaceRepository(private val placeDao: PlaceDao) {

    companion object {

        private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao): PlaceRepository {
            if (instance == null) {
                synchronized(PlaceRepository::class.java) {
                    if (instance == null) {
                        instance = PlaceRepository(placeDao)
                    }
                }
            }
            return instance!!
        }
    }

    suspend fun getProvinceList() = withContext(Dispatchers.IO) {
        var list = placeDao.getProvinceList()
        if (list.isEmpty()) {
            list = create<PlaceService>().getProvinces().await()
            placeDao.saveProvinceList(list)
        }
        list
    }

    suspend fun getCityList(provinceId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCityList(provinceId)
        if (list.isEmpty()) {
            list = create<PlaceService>().getCities(provinceId).await()
            for (city in list) {
                city.provinceId = provinceId
            }
            placeDao.saveCityList(list)
        }
        list
    }

    suspend fun getCountyList(provinceId: Int, cityId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCountyList(cityId)
        if (list.isEmpty()) {
            list = create<PlaceService>().getCounties(provinceId, cityId).await()
            placeDao.saveCountyList(list)
        }
        list
    }
}