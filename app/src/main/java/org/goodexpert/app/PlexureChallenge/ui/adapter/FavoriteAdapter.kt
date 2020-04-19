package org.goodexpert.app.PlexureChallenge.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.schedulers.Schedulers
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.goodexpert.app.PlexureChallenge.viewmodel.AppViewModel

class FavoriteAdapter : BaseAdapter<Store> {

    private val appViewModel: AppViewModel
    private var sorted: String? = null

    constructor(activity: Activity) : super(activity) {
        appViewModel = ViewModelProviders.of(activity as AppCompatActivity).get(AppViewModel::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(getInflater().inflate(R.layout.view_favorite_item, parent, false))
    }

    fun setSorted(sorted: String?) {
        if (this.sorted != sorted) {
            this.sorted = sorted
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder : BaseViewHolder<Store> {
        private val txtName: TextView
        private val txtAddress: TextView
        private val txtDistance: TextView
        private val txtFeatures: TextView
        private val btnDelete: Button

        constructor(itemView: View) : super(itemView) {
            this.txtName = itemView.findViewById(R.id.txtName)
            this.txtAddress = itemView.findViewById(R.id.txtAddress)
            this.txtDistance = itemView.findViewById(R.id.txtDistance)
            this.txtFeatures = itemView.findViewById(R.id.txtFeatures)
            this.btnDelete = itemView.findViewById(R.id.btnDelete)
        }

        override fun onBindViewHolder(position: Int, data: Store) {
            txtName.apply {
                this.text = data.name
            }

            txtAddress.apply {
                this.text = data.address
            }

            txtDistance.apply {
                this.text = "${data.distance}Km"
            }

            txtFeatures.apply {
                data.featureList?.let {
                    var text = ""
                    for (feature in it) {
                        if (!text.isEmpty()) {
                            text += ", ${feature}"
                        } else {
                            text = feature
                        }
                    }
                    this.text = text
                }
            }

            btnDelete.apply {
                this.setOnClickListener {
                    data.isFavorite = false;
                    appViewModel.updateStore(data)
                        .subscribeOn(Schedulers.io())
                        .subscribe({

                        }, {
                            it.printStackTrace()
                        })
                }
            }
        }

        private fun sortedBy(featureList: List<String>): List<String> {
            when (sorted) {
                "asc" -> {
                    return featureList.sorted()
                }
                "desc" -> {
                    return featureList.sortedDescending()
                }
                else -> {
                    return featureList
                }
            }
        }
    }
}