package com.example.test_itunes_search.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_itunes_search.App
import com.example.test_itunes_search.R
import com.example.test_itunes_search.di.component.ViewModelComponent
import com.example.test_itunes_search.presentation.adapter.SearchResultAdapter
import com.example.test_itunes_search.utils.hideKeyboardEx
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var viewModel: SearchResultViewModel

    private var pageCount: Int = 0

    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()

        initViews()
        initRecyclerView()
    }

    override fun onDestroy() {
        viewModel.clearData()
        recyclerView.adapter = null
        super.onDestroy()
    }

    private fun createDaggerDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }

    private fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    private fun initRecyclerView() {
        searchResultAdapter = SearchResultAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.rvSearchResult).apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initViews() {
        findViewById<Button>(R.id.btnSearch).setOnClickListener { this.hideKeyboardEx()
            viewModel.apply {
                searchText = findViewById<EditText>(R.id.etTerm).text.toString()
                itemsLiveData.observe(this@MainActivity, {
                    searchResultAdapter.updateData(it)
                })
                getSearchResult()
            }
        }
    }
}