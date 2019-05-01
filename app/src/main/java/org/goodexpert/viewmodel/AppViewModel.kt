package org.goodexpert.viewmodel

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.Completable
import org.goodexpert.data.entity.Store
import org.goodexpert.repository.StoreRepository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val storeRepository: StoreRepository
    private val storesLiveData: LiveData<List<Store>>
    private val isRefreshingLiveData =  MutableLiveData<Boolean>()

    init {
        storeRepository = StoreRepository(application)
        storesLiveData = storeRepository.getStores()
    }

    override fun onCleared() {
        super.onCleared()
        storeRepository.clear()
    }

    fun getStores(): LiveData<List<Store>> {
        return storesLiveData
    }

    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshingLiveData
    }

    @Synchronized
    fun fetchStores() {
        if (isRefreshingLiveData.value == true) {
            return
        }
        isRefreshingLiveData.value = true

        storeRepository.fetchStores {
            isRefreshingLiveData.value = false
        }
    }

    fun updateStore(store: Store): Completable {
        return storeRepository.updateStore(store)
    }
}