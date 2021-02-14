package com.optus.employee

import android.app.Application
import com.optus.employee.di.AppComponent
import com.optus.employee.di.DaggerAppComponent
import timber.log.Timber

class UserInfoApplication : Application() {

    val appComponent: AppComponent by lazy { initalizeAppComponent() }

    private fun initalizeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}