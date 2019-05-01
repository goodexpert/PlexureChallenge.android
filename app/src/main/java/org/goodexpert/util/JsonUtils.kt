package org.goodexpert.util

import android.text.TextUtils
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by steve on 16/04/19.
 */
object JsonUtils {

    private val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    val defaultGson: Gson by lazy {
        GsonBuilder()
//                .serializeNulls()
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .create()
    }

    fun getJsonBigDecimal(element: JsonElement?): BigDecimal? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asBigDecimal
    }

    fun getJsonBigInteger(element: JsonElement?): BigInteger? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asBigInteger
    }

    fun getJsonBoolean(element: JsonElement?): Boolean? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asBoolean
    }

    fun getJsonByte(element: JsonElement?): Byte? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asByte
    }

    fun getJsonCharacter(element: JsonElement?): Char? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asCharacter
    }

    fun getJsonDouble(element: JsonElement?): Double? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asDouble
    }

    fun getJsonFloat(element: JsonElement?): Float? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asFloat
    }

    fun getJsonInt(element: JsonElement?): Int? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asInt
    }

    fun getJsonLong(element: JsonElement?): Long? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asLong
    }

    fun getJsonNumber(element: JsonElement?): Number? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asNumber
    }

    fun getJsonShort(element: JsonElement?): Short? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asShort
    }

    fun getJsonString(element: JsonElement?): String? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.asString
    }

    fun getJsonStringObject(element: JsonElement?): String? {
        if (element == null || element is JsonNull) {
            return null
        }
        return element.toString()
    }

    fun <T: Any> getJsonObject(element: JsonElement?, type: Type): T? {
        var obj: T? = null
        if (element != null && !(element is JsonNull)) {
            try {
                obj = defaultGson.fromJson(element, type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return obj
    }

    fun <T: Any> getJsonObject(json: String?, type: Type): T? {
        var obj: T? = null
        if (!TextUtils.isEmpty(json)) {
            try {
                obj = defaultGson.fromJson(json, type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return obj
    }

    fun <T: Any> getJsonList(element: JsonElement?): List<T>? {
        var objList: List<T>? = null
        if (element != null && !(element is JsonNull)) {
            try {
                objList = defaultGson.fromJson(element, object : TypeToken<List<T>>(){}.type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return objList
    }

    fun <T: Any> getJsonList(json: String?): List<T>? {
        var objList: List<T>? = null
        if (json != null) {
            try {
                objList = defaultGson.fromJson(json, object : TypeToken<List<T>>(){}.type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return objList
    }

    fun <T: Any> getJsonList(element: JsonElement?, type: Type): ArrayList<T>? {
        var objList: ArrayList<T>? = null
        if (element != null && !(element is JsonNull)) {
            try {
                objList = defaultGson.fromJson(element, type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return objList
    }

    fun <T: Any> getJsonList(json: String?, type: Type): ArrayList<T>? {
        var objList: ArrayList<T>? = null
        if (json != null) {
            try {
                objList = defaultGson.fromJson(json, type)
            } catch (ex: JsonSyntaxException) {
                ex.printStackTrace()
            }
        }
        return objList
    }

    fun toJson(obj: Any): String? {
        var stringJson: String? = null
        try {
            stringJson = defaultGson.toJson(obj)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return stringJson
    }
}