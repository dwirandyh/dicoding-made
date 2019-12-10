package com.dwirandyh.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dwirandyh.movieapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupBottomNavigation()
    }

    /**
     * Setup Bottom Navigation View with Navigation Component
     *
     */
    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)

        // Register menu which we don't want to display the back button
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_movie, R.id.nav_tv_show, R.id.nav_favorite, R.id.nav_setting
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_navigation.setupWithNavController(navController)

        setupToolbar(navController, appBarConfiguration)

        handleToolbar()
    }

    /**
     * Register toolbar into nav controller
     * so the up button/back button will work well
     *
     * @param navController
     * @param appBarConfiguration
     */
    private fun setupToolbar(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun handleToolbar() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.nav_home) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }
    }
}
