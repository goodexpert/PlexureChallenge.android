package org.goodexpert.repository

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.goodexpert.data.LocalDatabase
import org.goodexpert.data.dao.StoreDao
import org.goodexpert.data.entity.Store
import org.goodexpert.network.WebService
import org.goodexpert.network.WebServiceFactory
import java.util.*
import java.util.concurrent.Callable

class StoreRepository(application: Application) {

    private var compositeDisposalble: CompositeDisposable? = null

    private val storeDao: StoreDao
    private val webService: WebService

    private val storesLiveData: LiveData<List<Store>>

    init {
        val database = LocalDatabase.getDatabase(application)
        this.storeDao = database.storeDao()
        this.webService = WebServiceFactory.getInstance()

        this.storesLiveData = storeDao.getAllItems()
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposalble == null || compositeDisposalble?.isDisposed!!) {
            compositeDisposalble = CompositeDisposable()
        }
        return compositeDisposalble!!
    }

    fun clear() {
        compositeDisposalble?.let {
            it.clear()
            compositeDisposalble = null
        }
    }

    fun fetchStores(action: () -> Unit) {
        val dispose = webService.getStores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateStores(it)
                action()
            }, {
                action()
            })
        getCompositeDisposable().add(dispose)
    }

    private fun updateStores(list: List<Store>?) {
        list?.let {
            for (store in it) {
                store.isFavorite = true
            }
        }

        list?.let {
            async {
                storeDao.deleteAll();
                storeDao.insertStores(list)
            }
        }
    }

    fun getStores(): LiveData<List<Store>> {
        return storeDao.getAllItems()
    }

    fun updateStore(store: Store): Completable {
        return storeDao.update(store)
    }

    private fun async(action: (() -> Unit)) {
        Observable.fromCallable { action() }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}