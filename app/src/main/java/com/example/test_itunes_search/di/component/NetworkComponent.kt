package com.example.test_itunes_search.di.component

import com.example.test_itunes_search.di.module.NetworkModule
import com.example.test_itunes_search.domain.NetworkUseCase
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    val networkUseCase: NetworkUseCase
}