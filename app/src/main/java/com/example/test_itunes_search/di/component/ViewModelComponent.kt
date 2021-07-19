package com.example.test_itunes_search.di.component

import com.example.test_itunes_search.di.module.ViewModelModule
import com.example.test_itunes_search.presentation.MainActivity
import dagger.Component

@Component(modules = [ViewModelModule::class], dependencies = [NetworkComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
}