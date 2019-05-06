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
    private var dataFiltered = listOf<T>()

    private var predicate: ((T) -> Boolean) = { true }

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
        if (dataSet != null) {
            dataOriginal.addAll(dataSet)
            dataFiltered = dataOriginal.filter { predicate(it) }
        }
        notifyDataSetChanged()
    }

    open fun filter(predicate: (T) -> Boolean) {
        if (this.predicate != predicate) {
            dataFiltered = dataOriginal.filter { predicate(it) }
            notifyDataSetChanged()
        }
        this.predicate = predicate
    }

    abstract class BaseViewHolder<T> : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)

        abstract fun onBindViewHolder(position: Int, data : T)
    }
}