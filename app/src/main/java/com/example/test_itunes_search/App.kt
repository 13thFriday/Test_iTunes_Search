package com.example.test_itunes_search

import android.app.Application
import com.example.test_itunes_search.di.component.DaggerNetworkComponent
import com.example.test_itunes_search.di.component.DaggerViewModelComponent
import com.example.test_itunes_search.di.component.ViewModelComponent
import com.example.test_itunes_search.di.module.NetworkModule
import com.example.test_itunes_search.di.module.ViewModelModule

class App: Application() {
    private var viewModelComponent: ViewModelComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        val networkComponent = DaggerNetworkComponent.builder()
            .networkModule(NetworkModule())
            .build()

        viewModelComponent = DaggerViewModelComponent.builder()
            .networkComponent(networkComponent)
            .viewModelModule(ViewModelModule())
            .build()
    }

    fun getViewModelComponent(): ViewModelComponent {
        return this!!.viewModelComponent!!
    }
}