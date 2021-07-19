package com.example.test_itunes_search.domain

import com.example.test_itunes_search.repository.models.SearchResult
import com.example.test_itunes_search.repository.networkapi.NetworkRepository
import com.example.test_itunes_search.utils.singleTransformation
import io.reactivex.Single

class NetworkUseCase(
    private val networkRepository: NetworkRepository
) {

    fun getSearchResults(term: String, offset: Int): Single<List<SearchResult>> {
        return networkRepository.getSearchResult(term, offset)
            .compose(singleTransformation())
            .map { response -> response.body()?.results ?: emptyList()}
    }
}