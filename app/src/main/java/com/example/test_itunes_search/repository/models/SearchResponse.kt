package com.example.test_itunes_search.repository.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("resultCount")
    val resultCount : Int,
    @SerializedName("results")
    val results : List<SearchResult>
)
