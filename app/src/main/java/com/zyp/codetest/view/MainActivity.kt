package com.zyp.codetest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyp.codetest.R
import com.zyp.codetest.adapter.MovieAdapter
import com.zyp.codetest.databinding.ActivityMainBinding
import com.zyp.codetest.databinding.ListItemMovieBinding
import com.zyp.codetest.model.Movie
import com.zyp.codetest.utils.Utils.loadImageFromGlide
import com.zyp.codetest.viewmodel.MainActivityViewModel
import com.zyp.codetest.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var detailViewModel: MovieDetailViewModel

    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        setUI()
        showProgressBar()
        getUpcomingMovies()
        getPopularMovies()
        getErrorMessage()

    }

    private fun setUI() {
        upcomingAdapter = MovieAdapter { movie, view -> bindAdapterView(movie, view) }

        binding.recyclerUpcoming.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        popularAdapter = MovieAdapter { movie, view -> bindAdapterView(movie, view) }

        binding.recyclerPopular.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        binding.layoutUpcomingRetry.setOnClickListener(this)
        binding.layoutPopularRetry.setOnClickListener(this)

    }

    private fun bindAdapterView(movie: Movie, view: View) {
        val binding = ListItemMovieBinding.bind(view)
        binding.apply {
            movieImage.loadImageFromGlide("https://image.tmdb.org/t/p/w300${movie.posterPath}")
            movieName.text = movie.title
        }

        binding.itemMovieCard.setOnClickListener { showMovieDetails(movie) }

        var favMovie: Movie? = null
        detailViewModel.getMovieById(movie).observe(this, {
            favMovie = it
            if (favMovie == null)
                binding.movieFavorite.setImageResource(R.drawable.ic_favorite_border)
            else
                binding.movieFavorite.setImageResource(R.drawable.ic_favorite)
        })

        binding.movieFavorite.setOnClickListener {
            if (favMovie == null) {
                detailViewModel.insertFavorite(movie)
                binding.movieFavorite.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(this, "Added to Favorite!", Toast.LENGTH_LONG).show()
            } else {
                detailViewModel.deleteFavorite(movie)
                binding.movieFavorite.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(this, "Removed From Favorite!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgressBar() {
        binding.upcomingLoading.visibility = View.VISIBLE
        binding.popularLoading.visibility = View.VISIBLE
        binding.layoutUpcomingRetry.visibility = View.GONE
        binding.layoutPopularRetry.visibility = View.GONE
    }

    private fun getErrorMessage() {
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun getUpcomingMovies() {
        viewModel.upcomingMovies.observe(this, Observer {
            binding.upcomingLoading.visibility = View.GONE
            binding.layoutUpcomingRetry.visibility = View.GONE
            if (it.isNotEmpty())
                upcomingAdapter.movieList = it
            else
                binding.layoutUpcomingRetry.visibility = View.VISIBLE

        })

        viewModel.getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModel.popularMovies.observe(this, Observer {
            binding.popularLoading.visibility = View.GONE
            binding.layoutPopularRetry.visibility = View.GONE
            if (it.isNotEmpty())
                popularAdapter.movieList = it
            else
                binding.layoutPopularRetry.visibility = View.VISIBLE
        })

        viewModel.getPopularMovies()
    }


    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_data", movie)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.layoutUpcomingRetry -> {
                binding.upcomingLoading.visibility = View.VISIBLE
                binding.layoutUpcomingRetry.visibility = View.GONE
                getUpcomingMovies()
            }
            binding.layoutPopularRetry -> {
                binding.popularLoading.visibility = View.VISIBLE
                binding.layoutPopularRetry.visibility = View.GONE
                getPopularMovies()
            }
        }
    }

}