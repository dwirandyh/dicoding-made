package com.dwirandyh.movieapp.repository

import android.content.Context
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.TvShow

class MovieRepository(private val context: Context) {

    fun getMovieList(): ArrayList<Movie> {
        val multiLanguageDescription = context.resources.getStringArray(R.array.movie_description)
        return arrayListOf(
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                "Avengers: Infinity War",
                "Joe Russo",
                149,
                8.5f,
                8.5f / 2,
                multiLanguageDescription[0]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
                "Spider-Man: Into the Spider-Verse",
                "Rodney Rothman",
                134,
                8.4f,
                8.4f / 2,
                multiLanguageDescription[1]
            )
            ,
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/uxzzxijgPIY7slzFvMotPv8wjKA.jpg",
                "Black Panther",
                "Ryan Coogler",
                134,
                7.4f,
                7.4f / 2,
                multiLanguageDescription[2]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5Kg76ldv7VxeX9YlcQXiowHgdX6.jpg",
                "Aquaman",
                "James Wan",
                144,
                6.8f,
                6.8f / 2,
                multiLanguageDescription[3]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                "Deadpool 2",
                "David Leitch",
                121,
                7.5f,
                7.5f / 2,
                multiLanguageDescription[4]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg",
                "Venom",
                "Ruben Fleischer",
                112,
                6.6f,
                6.6f / 2,
                multiLanguageDescription[5]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/eivQmS3wqzqnQWILHLc4FsEfcXP.jpg",
                "Ant-Man and the Wasp",
                "Peyton Reed",
                149,
                7.0f,
                7.0f / 2,
                multiLanguageDescription[6]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg",
                "Bumblebee",
                "Travis Knight",
                149,
                6.5f,
                6.5f / 2,
                multiLanguageDescription[7]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/2PKXkTik1sOICveKTNh6BspD5Xj.jpg",
                "High Strung Free Dance",
                "Michael Damian",
                103,
                7.2f,
                7.2f / 2,
                multiLanguageDescription[8]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/AkJQpZp9WoNdj7pLYSj1L0RcMMN.jpg",
                "Mission: Impossible - Fallout",
                "Christopher McQuarrie",
                148,
                7.3f,
                7.3f / 2,
                multiLanguageDescription[9]
            ),
            Movie(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/pU1ULUq8D3iRxl1fdX2lZIzdHuI.jpg",
                "Ready Player One",
                "Steven Spielberg",
                149,
                7.6f,
                7.6f / 2,
                multiLanguageDescription[10]
            )
        )
    }

    fun getTvShowList(): ArrayList<TvShow> {
        val multiLanguageDescription = context.resources.getStringArray(R.array.tv_show_description)
        return arrayListOf(
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/BbNvKCuEF4SRzFXR16aK6ISFtR.jpg",
                "The Mandalorian",
                "Jon Favreau",
                8,
                7.7f,
                7.5f / 2,
                multiLanguageDescription[0]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                "Rick and Morty ",
                "Dan Harmon",
                19,
                8.6f,
                8.6f / 2,
                multiLanguageDescription[1]
            )
            ,
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
                "Arrow",
                "Greg Berlanti",
                12,
                5.8f,
                5.8f / 2,
                multiLanguageDescription[2]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/yTZQkSsxUFJZJe67IenRM0AEklc.jpg",
                "Matt Groening",
                "James Wan",
                10,
                7.2f,
                7.2f / 2,
                multiLanguageDescription[3]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/xOjRNnQw5hqR1EULJ2iHkGwJVA4.jpg",
                "His Dark Materials",
                "Philip Pullman",
                8,
                7.8f,
                7.8f / 2,
                multiLanguageDescription[4]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
                "The Flash",
                "Geoff Johns",
                9,
                6.7f,
                6.7f / 2,
                multiLanguageDescription[5]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg",
                "Supernatural",
                "Eric Kripke",
                8,
                7.4f,
                7.4f / 2,
                multiLanguageDescription[6]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/reKs8y4mPwPkZG99ZpbKRhBPKsX.jpg",
                "The Walking Dead",
                "Frank Darabont",
                9,
                7.3f,
                7.3f / 2,
                multiLanguageDescription[7]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6t6r1VGQTTQecN4V0sZeqsmdU9g.jpg",
                "Law & Order: Special Victims Unit",
                "Dick Wolf",
                9,
                6.5f,
                6.5f / 2,
                multiLanguageDescription[8]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/4X7o1ssOEvp4BFLim1AZmPNcYbU.jpg",
                "Riverdale",
                "Roberto Aguirre-Sacasa",
                11,
                7.3f,
                7.3f / 2,
                multiLanguageDescription[9]
            ),
            TvShow(
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jnsvc7gCKocXnrTXF6p03cICTWb.jpg",
                "Grey's Anatomy ",
                "Steven Spielberg",
                9,
                6.4f,
                6.4f / 2,
                multiLanguageDescription[10]
            )
        )
    }
}