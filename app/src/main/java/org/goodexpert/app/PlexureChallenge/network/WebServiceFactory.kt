package org.goodexpert.app.PlexureChallenge.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.goodexpert.app.PlexureChallenge.BuildConfig
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebServiceFactory {

    private const val BASE_URL = "https://bitbucket.org/YahiaRagaePlex/plexure-challenge/"

    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L

    @Volatile private var instance: WebService? = null

    fun getInstance(): WebService {
        return instance ?: synchronized(WebServiceFactory) {
            instance ?: create(createHttpClient(), createJsonConverterFactory()).also { instance = it }
        }
    }

    val defaultGson: Gson by lazy {
        GsonBuilder()
//            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()
    }

    private fun createHttpClient(interceptor: Interceptor? = null, authenticator: Authenticator? = null): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

        if (interceptor != null) {
            builder.addInterceptor(interceptor)
        }

        if (authenticator != null) {
            builder.authenticator(authenticator)
        }

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor())
        }
        return builder.build()
    }

    private fun createJsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(defaultGson)
    }

    private fun create(client: OkHttpClient = OkHttpClient(),
                       converterFactory: Converter.Factory = GsonConverterFactory.create()): WebService {
        // Create a Retrofit instance.
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build()

        // Create an implementation of the endpoint API service.
        return retrofit.create(WebService::class.java)
    }

    fun <T> mySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}