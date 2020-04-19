package org.goodexpert.app.PlexureChallenge.viewmodel

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.Completable
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.goodexpert.app.PlexureChallenge.repository.StoreRepository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val storeRepository: StoreRepository
    private val storesLiveData: LiveData<List<Store>>
    private val isRefreshingLiveData =  MutableLiveData<Boolean>()
    private val sortedLiveData =  MutableLiveData<String?>()
    private val featuresLiveData = MutableLiveData<List<String>?>()

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

    fun getSortedBy(): LiveData<String?> {
        return sortedLiveData
    }

    fun setSortedBy(sorted: String?) {
        sortedLiveData.value = sorted
    }

    fun getFeatures(): LiveData<List<String>?> {
        return featuresLiveData
    }

    fun setFeatures(features: List<String>?) {
        featuresLiveData.value = features
    }
}