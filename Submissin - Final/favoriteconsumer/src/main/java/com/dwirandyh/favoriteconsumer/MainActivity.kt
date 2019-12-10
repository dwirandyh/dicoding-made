package com.dwirandyh.favoriteconsumer

import android.database.ContentObserver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwirandyh.favoriteconsumer.adapter.MovieAdapter
import com.dwirandyh.favoriteconsumer.model.Movie
import com.dwirandyh.favoriteconsumer.utils.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        setupMovieList()

        observeMovieProvider()

        loadMovieAsync()
    }

    private fun setupMovieList() {
        rv_movie.layoutManager = LinearLayoutManager(this)
        rv_movie.setHasFixedSize(true)
        rv_movie.adapter = adapter
    }

    private fun observeMovieProvider() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadMovieAsync()
            }
        }

        contentResolver.registerContentObserver(Movie.CONTENT_URI, true, myObserver)
    }

    private fun loadMovieAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_bar.visibility = View.VISIBLE
            val deferredMovies = async(Dispatchers.IO) {
                var movieList = ArrayList<Movie>()
                var cursor: Cursor? = null
                try {

                    cursor =
                        contentResolver.query(Movie.CONTENT_URI, null, null, null, null) as Cursor
                    movieList = MappingHelper.mapCursorToMovieArrayList(cursor)
                } finally {
                    cursor?.close()
                }

                movieList
            }

            val movieList = deferredMovies.await()
            progress_bar.visibility = View.INVISIBLE
            if (movieList.size > 0) {
                adapter.movieList = movieList
            }
        }
    }
}
