package com.zwonb.coolweatherjetpack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zwonb.coolweatherjetpack.R
import com.zwonb.coolweatherjetpack.ui.area.ChooseAreaFragment
import com.zwonb.coolweatherjetpack.util.InjectorUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel =
            ViewModelProvider(this, InjectorUtil.getMainModelFactory())[MainViewModel::class.java]
        if (viewModel.isWeatherCached()) {

        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commit()
        }
    }
}
