package com.dwirandyh.movieapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "99bc66d74f8686fc34d985741a078dc0"

    private fun retrofitService(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    fun movieEndpoint(): MovieEndpoint = retrofitService().create(MovieEndpoint::class.java)

    fun tvShowEndpoint(): TvShowEndpoint = retrofitService().create(TvShowEndpoint::class.java)
}