package com.dwirandyh.movieapp.repository

import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie

class MovieRepository {
    fun getMovieList(): ArrayList<Movie> {
        return arrayListOf(
            Movie(
                R.drawable.avenger_infinity_war,
                "Avengers: Infinity War",
                "Joe Russo",
                149,
                8.5f,
                8.5f / 2,
                "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain."
            ),
            Movie(
                R.drawable.spider_man,
                "Spider-Man: Into the Spider-Verse",
                "Rodney Rothman",
                134,
                8.4f,
                8.4f / 2,
                "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension."
            )
            ,
            Movie(
                R.drawable.black_panther,
                "Black Panther",
                "Ryan Coogler",
                134,
                7.4f,
                7.4f / 2,
                "King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war."
            ),
            Movie(
                R.drawable.aquaman,
                "Aquaman",
                "James Wan",
                144,
                6.8f,
                6.8f / 2,
                "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne."
            ),
            Movie(
                R.drawable.deadpool,
                "Deadpool 2",
                "David Leitch",
                121,
                7.5f,
                7.5f / 2,
                "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life."
            ),
            Movie(
                R.drawable.venom,
                "Venom",
                "Ruben Fleischer",
                112,
                6.6f,
                6.6f / 2,
                "Investigative journalist Eddie Brock attempts a comeback following a scandal, but accidentally becomes the host of Venom, a violent, super powerful alien symbiote. Soon, he must rely on his newfound powers to protect the world from a shadowy organization looking for a symbiote of their own."
            ),
            Movie(
                R.drawable.antman,
                "Ant-Man and the Wasp",
                "Peyton Reed",
                149,
                7.0f,
                7.0f / 2,
                "Just when his time under house arrest is about to end, Scott Lang once again puts his freedom at risk to help Hope van Dyne and Dr. Hank Pym dive into the quantum realm and try to accomplish, against time and any chance of success, a very dangerous rescue mission."
            ),
            Movie(
                R.drawable.bumblebee,
                "Bumblebee",
                "Travis Knight",
                149,
                6.5f,
                6.5f / 2,
                "On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken. When Charlie revives him, she quickly learns this is no ordinary yellow VW bug."
            ),
            Movie(
                R.drawable.high_strung,
                "High Strung Free Dance",
                "Michael Damian",
                103,
                7.2f,
                7.2f / 2,
                "Zander Raines, a dazzling and tempestuous young choreographer, gives the break of a lifetime to two hopeful artists when he casts a stunning contemporary dancer, Barlow, and innovative pianist, Charlie, in New York’s most-anticipated new Broadway show: Free Dance. But the move throws off the show’s delicate creative balance when Charlie falls hard for Barlow while Zander embraces her as his muse."
            ),
            Movie(
                R.drawable.mission_impossible,
                "Mission: Impossible - Fallout",
                "Christopher McQuarrie",
                148,
                7.3f,
                7.3f / 2,
                "When an IMF mission ends badly, the world is faced with dire consequences. As Ethan Hunt takes it upon himself to fulfill his original briefing, the CIA begin to question his loyalty and his motives. The IMF team find themselves in a race against time, hunted by assassins while trying to prevent a global catastrophe."
            ),
            Movie(
                R.drawable.ready_player_one,
                "Ready Player One",
                "Steven Spielberg",
                149,
                7.6f,
                7.6f / 2,
                "When the creator of a popular video game system dies, a virtual contest is created to compete for his fortune."
            )
        )
    }
}