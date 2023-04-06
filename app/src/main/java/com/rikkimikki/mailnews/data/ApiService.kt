package com.rikkimikki.mailnews.data

import com.rikkimikki.mailnews.domain.pojo.ArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @GET("everything/")
    fun getArticles(
        @Query("page") page : Int = 1,
        @Query("q") q :String = "",
        @Query("language") language :String = "en",
        @Query("pageSize") pageSize :Int = 20,
        @Query("apiKey") apiKey :String = "177d68cbb2cf4511a899016df23da9fd", //Yes, I know that everyone will see my api key
    ): Observable<ArticleResponse>
}