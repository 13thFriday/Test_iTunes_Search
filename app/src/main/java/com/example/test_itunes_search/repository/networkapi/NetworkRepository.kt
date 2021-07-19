package com.example.test_itunes_search.repository.networkapi

import com.example.test_itunes_search.utils.Constants

class NetworkRepository(private val networkNetworkApi: NetworkApi) {

    fun getSearchResult(term: String, offset: Int) =
        networkNetworkApi.getSearchResult(term, offset, Constants.ITEMS_PER_REQUEST)

}