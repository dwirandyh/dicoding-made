package com.dwirandyh.movieapp.view

import android.widget.ListView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import kotlinx.android.synthetic.main.activity_movie_list.view.*
import org.hamcrest.CoreMatchers.anything
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieListActivityTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(MovieListActivity::class.java)

    @Test
    fun assertMovieListCount() {
        val listView: ListView = mActivityRule.activity.findViewById(R.id.lv_movie)
        val itemCount = listView.count
        assertTrue(itemCount > 0)
    }

    @Test
    fun assertMovieItemClicked() {
        // Click first item in movie list
        onData(anything()).inAdapterView(withId(R.id.lv_movie)).atPosition(0).perform(click())

        //check if description show
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
    }
}