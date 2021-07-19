package com.example.test_itunes_search.di.module

import com.example.test_itunes_search.domain.NetworkUseCase
import com.example.test_itunes_search.presentation.SearchResultViewModel
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    internal fun providesSearchResultViewModel(networkUseCase: NetworkUseCase): SearchResultViewModel {
        return SearchResultViewModel(networkUseCase)
    }
}