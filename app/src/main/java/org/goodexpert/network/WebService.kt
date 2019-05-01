package org.goodexpert.network

import io.reactivex.Observable
import org.goodexpert.data.entity.Store
import retrofit2.http.GET

interface WebService {

    @GET("raw/449a2452c03961d5d1a094af524148cc345523db/data.json")
    fun getStores(
    ): Observable<List<Store>>
}