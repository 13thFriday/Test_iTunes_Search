package com.example.myktl.di.module

import com.example.myktl.presentation.SearchResultViewModel
import com.example.myktl.usecase.NetworkUseCase
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    internal fun providesSearchResultViewModel(networkUseCase: NetworkUseCase): SearchResultViewModel {
        return SearchResultViewModel(networkUseCase)
    }
}