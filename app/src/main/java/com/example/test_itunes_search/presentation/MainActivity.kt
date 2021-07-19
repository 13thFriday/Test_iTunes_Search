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
import com.example.test_itunes_search.utils.showSnackBar
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var viewModel: SearchResultViewModel

    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()

        initViews()
        initRecyclerView()
        viewModel.apply {
            itemsLiveData.observe(this@MainActivity, {
                searchResultAdapter.updateData(it)
            })
            errorsLiveData.observe(this@MainActivity, {
                when (it) {
                    "reset" -> searchResultAdapter.resetList()
                    else -> showSnackBar(recyclerView, it)
                }
            })
        }
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
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        searchResultAdapter = SearchResultAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.rvSearchResult).apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(dividerItemDecoration)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (dy > 0) viewModel.tryPaginate((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition())
                    }
                })
        }
        ResourcesCompat.getDrawable(baseContext.resources, R.drawable.divider, null)?.let { dividerItemDecoration.setDrawable(it) }
    }

    private fun initViews() {
        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            this.hideKeyboardEx()
            viewModel.apply {
                searchText = findViewById<EditText>(R.id.etTerm).text.toString()
                getSearchResult()
            }
        }
    }
}