package com.dwirandyh.movieapp.repository

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepository

    @Before
    fun before() {
        movieRepository = MovieRepository()
    }

    @Test
    fun getMovieList() {
        val movieList = movieRepository.getMovieList()
        assertTrue(movieList.size > 10)
    }
}