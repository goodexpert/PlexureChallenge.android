package org.goodexpert.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import org.goodexpert.data.entity.Store

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(store: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStores(stores: List<Store>)

    @Update
    fun update(store: Store): Completable

    @Query("DELETE FROM stores")
    fun deleteAll()

    @Query("SELECT * from stores ORDER BY id ASC")
    fun getAllItems(): LiveData<List<Store>>
}