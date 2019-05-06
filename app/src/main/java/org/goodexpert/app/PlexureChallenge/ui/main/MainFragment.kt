package org.goodexpert.app.PlexureChallenge.ui.main

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.goodexpert.app.PlexureChallenge.ui.BaseFragment
import org.goodexpert.app.PlexureChallenge.viewmodel.AppViewModel

class MainFragment : BaseFragment() {

    private lateinit var appViewModel: AppViewModel

    private var featureAll: HashSet<String> = hashSetOf()
    private var featureSet: HashSet<String> = hashSetOf()
    private var sortedBy: String? = null

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        appViewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)
        appViewModel.isRefreshing().observe(activity!!, Observer<Boolean> {
            refreshLayout.isRefreshing = it
        })

        appViewModel.getStores().observe(activity!!, Observer<List<Store>> {
            val favorites = it.filter { it.isFavorite }
            updateBadge(favorites.size)

            if (it.size == 0) {
                appViewModel.fetchStores()
            }

            featureAll.clear()
            for (item in it) {
                featureAll.addAll(item.featureList ?: listOf())
            }
        })

        appViewModel.getSortedBy().observe(activity!!, Observer<String?> {
            sortedBy = it
            activity?.invalidateOptionsMenu()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (sortedBy) {
            "asc" -> {
                menu?.findItem(R.id.action_sort)?.let {
                    it.isChecked = true
                }
            }
            "desc" -> {
                menu?.findItem(R.id.action_sort_desc)?.let {
                    it.isChecked = true
                }
            }
            else -> {
                menu?.findItem(R.id.action_sort_none)?.let {
                    it.isChecked = true
                }
            }
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                onFilter()
            }
            R.id.action_sort -> {
                appViewModel.setSortedBy("asc")
            }
            R.id.action_sort_desc -> {
                appViewModel.setSortedBy("desc")
            }
            R.id.action_sort_none -> {
                appViewModel.setSortedBy(null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onFilter() {
        val items = featureAll.toTypedArray()
        items.sort()

        val checkedItems = items.map { featureSet.contains(it) }.toBooleanArray()
        var featureSet = featureSet.clone() as HashSet<String>

        AlertDialog.Builder(activity!!)
            .setTitle("Test")
            .setMultiChoiceItems(items, checkedItems, { dialog, which, isChecked ->
                if (isChecked) {
                    featureSet.add(items[which])
                } else {
                    featureSet.remove(items[which])
                }
            })
            .setNegativeButton("Cancel", { dialog, which ->

            })
            .setPositiveButton("OK", { dialog, which ->
                this.featureSet = featureSet
                appViewModel.setFeatures(featureSet.toList())
            })
            .show()
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