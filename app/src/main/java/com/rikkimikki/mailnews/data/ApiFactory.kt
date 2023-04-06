package com.rikkimikki.mailnews.data

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://newsapi.org/v2/"

    fun getApiService(): ApiService {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }

}