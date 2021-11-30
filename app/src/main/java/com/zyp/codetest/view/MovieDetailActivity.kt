package com.zyp.codetest.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zyp.codetest.R
import com.zyp.codetest.databinding.ActivityMovieDetailBinding
import com.zyp.codetest.model.Movie
import com.zyp.codetest.utils.Utils.loadImageFromGlide
import com.zyp.codetest.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    private var favMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val movieData: Movie? = extras?.getParcelable("movie_data")
        if (movieData != null) {

            viewModel.getMovieById(movieData).observe(this, {
                favMovie = it
                bindDetails(movieData)
            })

            binding.floatingActionButton.setOnClickListener {
                if (favMovie == null) {
                    viewModel.insertFavorite(movieData)
                    binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)
                    Toast.makeText(this, "Added to Favorite!", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.deleteFavorite(movieData)
                    binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
                    Toast.makeText(this, "Removed From Favorite!", Toast.LENGTH_LONG).show()
                }
            }

        } else {
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_LONG).show()
            finish()
        }
    }


    private fun bindDetails(movie: Movie) {
        binding.movieBackdrop.loadImageFromGlide("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        binding.moviePoster.loadImageFromGlide("https://image.tmdb.org/t/p/w300${movie.posterPath}")

        binding.movieTitle.text = movie.title
        binding.movieRating.text = movie.rating.toString()
        binding.movieReleaseDate.text = movie.releaseDate
        binding.movieOverview.text = movie.overview

        if (favMovie == null)
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
        else
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)


    }
}
