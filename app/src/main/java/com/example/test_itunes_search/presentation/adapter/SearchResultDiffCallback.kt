package com.example.test_itunes_search.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.test_itunes_search.repository.models.SearchResult

class SearchResultDiffCallback(private val oldList: List<SearchResult>,
                               private val newList: List<SearchResult>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}