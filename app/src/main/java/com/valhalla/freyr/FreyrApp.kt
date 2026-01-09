package com.valhalla.freyr

import android.app.Application
import com.valhalla.freyr.di.dataModule
import com.valhalla.freyr.di.domainModule
import com.valhalla.freyr.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FreyrApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FreyrApp)
            modules(dataModule, domainModule, viewModelModule)
        }
    }
}
