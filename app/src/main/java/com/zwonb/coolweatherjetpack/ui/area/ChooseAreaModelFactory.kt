package com.zwonb.coolweatherjetpack.ui.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zwonb.coolweatherjetpack.data.repo.PlaceRepository

/**
 * @author zwonb
 * @date 2019-11-01
 */
class ChooseAreaModelFactory(private val repository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChooseAreaViewModel(repository) as T
    }
}