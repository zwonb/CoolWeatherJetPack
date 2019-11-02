package com.zwonb.coolweatherjetpack.ui.weather

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zwonb.coolweatherjetpack.R
import com.zwonb.coolweatherjetpack.databinding.ActivityWeatherBinding
import com.zwonb.coolweatherjetpack.util.InjectorUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*

/**
 * @author zwonb
 * @date 2019-11-02
 */
class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getWeatherModelFactory()
        )[WeatherViewModel::class.java]
    }

    private val bingding by lazy {
        DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        bingding.viewModel = viewModel
        bingding.resId = R.color.colorPrimary
        bingding.lifecycleOwner = this

        viewModel.weatherId = if (viewModel.isWeatherCached()) {
            viewModel.getWeatherCached().basic.weatherId
        } else {
            intent.getStringExtra("weather_id") ?: ""
        }

        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        viewModel.getWeather()
    }
}