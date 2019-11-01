package com.zwonb.coolweatherjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal

/**
 * @author zwonb
 * @date 2019-10-31
 */
class CoolWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context = this
    }
}