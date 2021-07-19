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
        getSearchResult(searchText, page)
        page++
    }

    fun tryPaginate(position: Int) {
        if (position + 2 > page * Constants.ITEMS_PER_REQUEST) { // page * ITEMS_PER_REQUEST = size of our list
            if(!newPostsLoading) {
                page++
                getSearchResult(searchText, page)
            }
        }
    }

    private fun getSearchResult(term: String, offset: Int) {
        disposables.add(
                networkUseCase.getSearchResults(term, offset)
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