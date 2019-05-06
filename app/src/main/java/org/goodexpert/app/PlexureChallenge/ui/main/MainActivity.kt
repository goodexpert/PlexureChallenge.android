package org.goodexpert.app.PlexureChallenge.ui.main

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.ui.BaseActivity
import org.goodexpert.app.PlexureChallenge.viewmodel.AppViewModel

class MainActivity : BaseActivity() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show and Manage the Drawer and Back Icon
        val topLevelIds = setOf(
            R.id.splashFragment,
            R.id.mainFragment
        )
        appBarConfiguration = AppBarConfiguration(topLevelIds)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)

        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener {
            appViewModel.fetchStores()
        }

        appViewModel.isRefreshing().observe(this, Observer<Boolean> {
            refreshLayout.isRefreshing = it
        })
    }

    override fun onBackPressed() {
        if (appBarConfiguration.getTopLevelDestinations().contains(navController.currentDestination?.id)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity()
            }
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return navController.navigateUp(appBarConfiguration)
    }
}