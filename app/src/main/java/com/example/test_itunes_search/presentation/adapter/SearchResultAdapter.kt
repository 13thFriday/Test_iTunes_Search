package com.example.test_itunes_search.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test_itunes_search.R
import com.example.test_itunes_search.repository.models.SearchResult

class SearchResultAdapter(private val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SONG = 0
        private const val TYPE_MOVIE = 1
        private const val TYPE_ANOTHER = 2
    }

    private var searchResult = ArrayList<SearchResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SONG -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.li_song, parent, false)
                SongViewHolder(view)
            }
            TYPE_MOVIE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.li_movie, parent, false)
                MovieViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.li_another_type, parent, false)
                AnotherTypeViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultItem = searchResult[position]
        when (holder) {
            is SongViewHolder -> holder.bind(searchResultItem)
            is MovieViewHolder -> holder.bind(searchResultItem)
            is AnotherTypeViewHolder -> holder.bind(searchResultItem)
            else -> throw IllegalArgumentException()
        }
    }

    fun updateData(searchList: List<SearchResult>) {

        val diffCallback = SearchResultDiffCallback(searchResult, searchList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        searchResult.addAll(searchList)

        diffResult.dispatchUpdatesTo(this)
    }

    fun resetList() {
        searchResult.clear()
    }


    override fun getItemCount(): Int {
        return searchResult.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (searchResult[position].kind) {
            "song" -> TYPE_SONG
            "feature-movie" -> TYPE_MOVIE
            else -> TYPE_ANOTHER
        }
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvWrapperType = itemView.findViewById<TextView>(R.id.tvWrapperType)
        private val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        private val tvTrackName = itemView.findViewById<TextView>(R.id.tvTrackName)

        fun bind(searchResult: SearchResult) {
            tvWrapperType.text = itemView.context.getString(R.string.song_label)
            tvArtistName.text = searchResult.artistName
            tvTrackName.text = searchResult.trackName
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvWrapperType = itemView.findViewById<TextView>(R.id.tvWrapperType)
        private val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        private val tvTrackName = itemView.findViewById<TextView>(R.id.tvTrackName)
        private val tvLongDescription = itemView.findViewById<TextView>(R.id.tvLongDescription)

        fun bind(searchResult: SearchResult) {
            tvWrapperType.text = itemView.context.getString(R.string.movie_label)
            tvArtistName.text = searchResult.artistName
            tvTrackName.text = searchResult.trackName
            tvLongDescription.text = searchResult.longDescription
        }
    }

    inner class AnotherTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvWrapperType = itemView.findViewById<TextView>(R.id.tvWrapperType)
        private val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        private val tvCollectionName = itemView.findViewById<TextView>(R.id.tvCollectionName)

        fun bind(searchResult: SearchResult) {
            tvWrapperType.text = searchResult.wrapperType
            tvArtistName.text = searchResult.artistName
            tvCollectionName.text = searchResult.collectionName
        }
    }
}