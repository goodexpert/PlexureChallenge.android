package org.goodexpert.data.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.goodexpert.data.converter.FeatureListConverter

@Entity(tableName = "stores")
data class Store(

    @PrimaryKey
    val id: Int,

    val name: String,

    val address: String,

    val latitude: Double,

    val longitude: Double,

    val distance: Long,

    @TypeConverters(FeatureListConverter::class)
    val featureList: ArrayList<String>?,

    var isFavorite: Boolean
) {
}