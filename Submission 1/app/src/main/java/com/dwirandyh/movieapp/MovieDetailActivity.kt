package com.dwirandyh.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var ivLogo: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvDescription: TextView
    private lateinit var rbRating: AppCompatRatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        ivLogo = findViewById(R.id.iv_logo)
        tvName = findViewById(R.id.tv_name)
        tvRating = findViewById(R.id.tv_rating)
        tvDescription = findViewById(R.id.tv_description)
        rbRating = findViewById(R.id.rb_rating)

        val selectedMovie: Movie = intent.getParcelableExtra("SelectedMovie")
        bind(selectedMovie)
    }

    private fun bind(movie: Movie) {
        ivLogo.setImageDrawable(getDrawable(movie.photo))
        tvName.text = movie.name
        tvRating.text = movie.rating.toString()
        tvDescription.text = movie.description
        rbRating.rating = movie.ratingStar
    }
}
