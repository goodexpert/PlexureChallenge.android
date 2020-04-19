package org.goodexpert.app.PlexureChallenge.data.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import org.goodexpert.app.PlexureChallenge.util.JsonUtils

class FeatureListConverter {

    @TypeConverter
    fun fromFeatureList(featureList: List<String>?): String? {
        return JsonUtils.toJson(ArrayList(featureList));
    }

    @TypeConverter
    fun toFeatureList(plainString: String?): ArrayList<String>? {
        return JsonUtils.getJsonList(plainString, object : TypeToken<List<String>>(){}.type)
    }
}