package com.rikkimikki.mailnews.domain.pojo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticleResponse (
    @SerializedName("articles")
    @Expose
    val data: List<Article>,

    @SerializedName("totalResults")
    @Expose
    val totalResults: Int,
)