package com.example.test_itunes_search.repository.networkapi

class NetworkRepository(private val networkNetworkApi: NetworkApi) {

    fun getSearchResult(term: String, offset: Int, limit: Int) =
        networkNetworkApi.getSearchResult(term, offset, limit)

}