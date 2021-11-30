package com.zyp.codetest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zyp.codetest.databinding.ListItemMovieBinding
import com.zyp.codetest.model.Movie
import com.zyp.codetest.utils.Utils.loadImageFromGlide
import javax.inject.Inject

class MovieAdapter @Inject constructor(private val bindViewHolder: (Movie, View) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ListItemMovieBinding,
        val bindViewHolder: (Movie, View) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) = bindViewHolder(item, binding.root)

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var movieList: List<Movie>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val binding =
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, bindViewHolder)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)

//        holder.binding.apply {
//            movieImage.loadImageFromGlide("https://image.tmdb.org/t/p/w300${movie.posterPath}")
//            movieName.text = movie.title
//        }
//
//        holder.binding.itemMovieCard.setOnClickListener { onMovieClick.invoke(movie) }
//
//        holder.binding.movieFavorite.setOnClickListener { onMovieClick.invoke(movie) }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}