package org.goodexpert.app.PlexureChallenge.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.goodexpert.app.PlexureChallenge.ui.BaseFragment
import org.goodexpert.app.PlexureChallenge.ui.adapter.FavoriteAdapter
import org.goodexpert.app.PlexureChallenge.viewmodel.AppViewModel

class FavoriteFragment : BaseFragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appViewModel = ViewModelProviders.of(activity!!).get(AppViewModel::class.java)
        adapter = FavoriteAdapter(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val decorator = DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(decorator)
        recyclerView.adapter = adapter

        appViewModel.getStores().observe(activity!!, Observer<List<Store>> {
            adapter.setItems(it.filter { it.isFavorite })
        })

        appViewModel.getSortedBy().observe(activity!!, Observer<String?> {
            adapter.setSorted(it)
        })

        appViewModel.getFeatures().observe(activity!!, Observer<List<String>?> { features ->
            adapter.filter {
                if (features?.isEmpty() ?: true) {
                    true
                } else {
                    it.featureList?.filter { features?.contains(it) ?: true }?.size ?: 0 > 0
                }
            }
        })
        return root
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(): Fragment {
            return FavoriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}