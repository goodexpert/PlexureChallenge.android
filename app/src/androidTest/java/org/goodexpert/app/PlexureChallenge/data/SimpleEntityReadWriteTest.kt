package org.goodexpert.app.PlexureChallenge.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.goodexpert.app.PlexureChallenge.data.dao.StoreDao
import org.goodexpert.app.PlexureChallenge.data.entity.Store
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    private lateinit var database: LocalDatabase
    private lateinit var storeDao: StoreDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        var context = ApplicationProvider.getApplicationContext<Context>()
        database = LocalDatabase.getDatabase(context)
        storeDao = database.storeDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanUp() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeStoreAndReadInList() {
        val store: Store = Store.Builder()
            .setId(100000)
            .setName("Test Unit Test")
            .setAddress("29 Becroft Drive, Forrest Hill, Auckland 0620")
            .setLatitude(-36.766342)
            .setLongitude(174.742654)
            .setDistance(12)
            .setFavorite(true)
            .build()
        storeDao.insert(store)

        storeDao.getAllItems().observeForever {
            assertEquals(true, it.contains(store))
        }
    }
}