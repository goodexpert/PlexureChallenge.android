package org.goodexpert.app.PlexureChallenge.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any>(@NonNull activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val activity: Activity
    private val inflater: LayoutInflater

    private val dataOriginal =  arrayListOf<T>()
    private val dataFiltered = arrayListOf<T>()

    init {
        this.activity = activity
        this.inflater = LayoutInflater.from(activity)
    }

    fun getInflater(): LayoutInflater {
        return inflater
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<T>)?.onBindViewHolder(position, getItem(position))
    }

    override fun getItemCount(): Int {
        return dataFiltered.size
    }

    fun getItem(position: Int) : T {
        return dataFiltered[position];
    }

    fun setItems(dataSet: List<T>?) {
        dataOriginal.clear()
        dataFiltered.clear()

        if (dataSet != null) {
            dataOriginal.addAll(dataSet)
            dataFiltered.addAll(toFiltered(dataOriginal))
        }
        notifyDataSetChanged()
    }

    open fun toFiltered(list: List<T>): List<T> {
        return list
    }

    abstract class BaseViewHolder<T> : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)

        abstract fun onBindViewHolder(position: Int, data : T)
    }
}