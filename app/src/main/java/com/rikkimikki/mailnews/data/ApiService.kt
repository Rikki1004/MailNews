package com.rikkimikki.mailnews.data

import com.rikkimikki.mailnews.domain.pojo.ArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @Headers("service-id: 25", "Content-Type: application/json")
    @GET("top-headlines/")
    fun getArticles(
        @Query("apiKey") apiKey :String = "404e1463c8d64e1f8470ab2c43864cbd", //Yes, I know that everyone will see my api key
        @Query("country") country :String = "us"
    ): Observable<ArticleResponse>
}