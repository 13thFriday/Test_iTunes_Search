package com.example.test_itunes_search.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_itunes_search.domain.NetworkUseCase
import com.example.test_itunes_search.repository.models.SearchResult
import com.example.test_itunes_search.utils.Constants
import io.reactivex.disposables.CompositeDisposable

class SearchResultViewModel(private val networkUseCase: NetworkUseCase) : ViewModel() {

    private var disposables = CompositeDisposable()
    private var page = 0
    private var newPostsLoading = false
    var searchText = ""

    val itemsLiveData: MutableLiveData<List<SearchResult>> by lazy {
        MutableLiveData<List<SearchResult>>()
    }

    fun getSearchResult() {
        getSearchResult(searchText, page, Constants.ITEMS_PER_REQUEST)
        page++
    }

    private fun getSearchResult(term: String, offset: Int, limit: Int) {
        disposables.add(
                networkUseCase.getSearchResults(term, offset, limit)
                        .doOnSubscribe { newPostsLoading = true }
                        .doOnError {
                            Log.e(this.javaClass.simpleName, it.message
                                    ?: "error class name - ${it.javaClass.simpleName}")
                        }
                        .doFinally { newPostsLoading = false }
                        .subscribe { list -> itemsLiveData.value = list }
        )
    }

    fun clearData() {
        disposables.dispose()
    }
}