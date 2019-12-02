package com.dwirandyh.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.MovieAdapter
import com.dwirandyh.movieapp.repository.MovieRepository

class MovieListActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var lvMovie: ListView
    private lateinit var adapter: MovieAdapter

    private var movieRepository: MovieRepository = MovieRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        toolbar = findViewById(R.id.toolbar)
        lvMovie = findViewById(R.id.lv_movie)

        toolbar.title = "Favorite Movie"
        setSupportActionBar(toolbar)

        initListView()
    }

    private fun initListView() {
        adapter =
            MovieAdapter(movieRepository.getMovieList(), this)
        lvMovie.adapter = adapter
        lvMovie.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = movieRepository.getMovieList()[position]

            val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java)
            intent.putExtra("SelectedMovie", selectedItem)
            startActivity(intent)
        }
    }
}
