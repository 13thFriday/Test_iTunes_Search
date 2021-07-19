package com.example.test_itunes_search.repository.networkapi

import com.example.test_itunes_search.repository.models.SearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("search?")
    fun getSearchResult(@Query("term") term: String,
                        @Query("offset") offset: Int,
                        @Query("limit") limit: Int): Single<Response<SearchResponse>>
}