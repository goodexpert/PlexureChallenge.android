package org.goodexpert.app.PlexureChallenge.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.goodexpert.app.PlexureChallenge.viewmodel.AppViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener {
            appViewModel.fetchStores()
        }

        appViewModel.isRefreshing().observe(this, Observer<Boolean> {
            refreshLayout.isRefreshing = it
        })

        appViewModel.getStores().observe(this, Observer<List<Store>> {
            val favorites = it.filter { it.isFavorite }
            updateBadge(favorites.size)

            if (it.size == 0) {
                appViewModel.fetchStores()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun updateBadge(count: Int) {
        postAction {
            val tabs: TabLayout = findViewById(R.id.tabs)
            val tab = tabs.getTabAt(1)

            tab?.let {
                val view: View
                if (tab.customView != null) {
                    view = tab.customView!!
                } else {
                    view = layoutInflater.inflate(R.layout.view_tab_item, null)
                    tab.customView = view
                }

                val txtBadge: TextView = view.findViewById(R.id.txtBadge)
                txtBadge.text = count.toString()
                if (count > 0) {
                    txtBadge.visibility = View.VISIBLE
                } else {
                    txtBadge.visibility = View.GONE
                }
            }
        }
    }

    fun postAction(action: (() -> Unit)?) {
        if (action == null)
            return

        Handler(Looper.getMainLooper()).post {
            if (!isFinishing()) {
                action()
            }
        }
    }

    fun postAction(action: (() -> Unit)?, delayMillis: Long) {
        if (action == null)
            return

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing()) {
                action()
            }
        }, delayMillis)
    }
}