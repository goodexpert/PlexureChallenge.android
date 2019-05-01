package org.goodexpert.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import org.goodexpert.R
import org.goodexpert.data.entity.Store
import org.goodexpert.ui.BaseFragment
import org.goodexpert.viewmodel.AppViewModel

class MainFragment : BaseFragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(activity!!, childFragmentManager)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        tabLayout = root.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        refreshLayout = root.findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener {
            appViewModel.fetchStores()
        }

        return root
    }

    private fun updateBadge(count: Int) {
        postAction {
            val tab = tabLayout.getTabAt(1)

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
}