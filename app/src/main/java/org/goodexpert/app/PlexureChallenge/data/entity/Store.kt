package org.goodexpert.app.PlexureChallenge.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.goodexpert.app.PlexureChallenge.data.converter.FeatureListConverter

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

    class Builder {

        private var _id: Int = 0

        private var _name: String = ""

        private var _address: String = ""

        private var _latitude: Double = 0.0

        private var _longitude: Double = 0.0

        private var _distance: Long = 0

        private var _featureList: ArrayList<String> = ArrayList()

        private var _isFavorite: Boolean = false

        fun build(): Store {
            return Store(_id, _name, _address, _latitude, _longitude, _distance, _featureList, _isFavorite)
        }

        fun setId(id: Int): Builder {
            this._id = id
            return this;
        }

        fun setName(name: String): Builder {
            this._name = name
            return this;
        }

        fun setAddress(address: String): Builder {
            this._address = address
            return this;
        }

        fun setLatitude(latitude: Double): Builder {
            this._latitude = latitude
            return this;
        }

        fun setLongitude(longitude: Double): Builder {
            this._longitude = longitude
            return this;
        }

        fun setDistance(distance: Long): Builder {
            this._distance = distance
            return this;
        }

        fun setFeatureList(featureList: ArrayList<String>): Builder {
            this._featureList = featureList
            return this;
        }

        fun setFavorite(isFavorite: Boolean): Builder {
            this._isFavorite = isFavorite
            return this;
        }
    }
}