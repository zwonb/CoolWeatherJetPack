package com.zwonb.coolweatherjetpack.ui.area

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zwonb.coolweatherjetpack.CoolWeatherApplication
import com.zwonb.coolweatherjetpack.data.model.place.City
import com.zwonb.coolweatherjetpack.data.model.place.County
import com.zwonb.coolweatherjetpack.data.model.place.Province
import com.zwonb.coolweatherjetpack.data.repo.PlaceRepository
import com.zwonb.coolweatherjetpack.ui.area.ChooseAreaFragment.Companion.LEVEL_CITY
import com.zwonb.coolweatherjetpack.ui.area.ChooseAreaFragment.Companion.LEVEL_COUNTY
import com.zwonb.coolweatherjetpack.ui.area.ChooseAreaFragment.Companion.LEVEL_PROVINCE
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author zwonb
 * @date 2019-11-01
 */
class ChooseAreaViewModel(private val repository: PlaceRepository) : ViewModel() {

    var currentLevel = MutableLiveData<Int>()
    var dataChanged = MutableLiveData<Int>()
    var isLoading = MutableLiveData<Boolean>()
    var areaSelected = MutableLiveData<Boolean>()

    var selectedProvince: Province? = null
    var selectedCity: City? = null
    var selectedCounty: County? = null

    lateinit var provinces: MutableList<Province>
    lateinit var cities: MutableList<City>
    lateinit var counties: MutableList<County>
    val dataList = ArrayList<String>()

    fun getProvinces() {
        currentLevel.value = LEVEL_PROVINCE
        launch {
            provinces = repository.getProvinceList()
            dataList.addAll(provinces.map { it.provinceName })
        }
    }

    fun getCities() = selectedProvince?.let {
        currentLevel.value = LEVEL_CITY
        launch {
            cities = repository.getCityList(it.provinceCode)
            dataList.addAll(cities.map { it.cityName })
        }
    }

    fun getCountries() = selectedCity?.let {
        currentLevel.value = LEVEL_COUNTY
        launch {
            counties = repository.getCountyList(it.provinceId, it.cityCode)
            dataList.addAll(counties.map { it.countyName })
        }
    }

    fun onListViewItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when {
            currentLevel.value == LEVEL_PROVINCE -> {
                selectedProvince = provinces[position]
                getCities()
            }
            currentLevel.value == LEVEL_CITY -> {
                selectedCity = cities[position]
                getCountries()
            }
            currentLevel.value == LEVEL_COUNTY -> {
                selectedCounty = counties[position]
                areaSelected.value = true
            }
        }
    }

    fun onBack() {
        if (currentLevel.value == LEVEL_COUNTY) {
            getCities()
        } else if (currentLevel.value == LEVEL_CITY) {
            getProvinces()
        }
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            dataList.clear()
            block()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(CoolWeatherApplication.context, e.message, Toast.LENGTH_SHORT).show()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        }
    }
}