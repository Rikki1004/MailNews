package com.rikkimikki.mailnews.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rikkimikki.mailnews.domain.pojo.Article

object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
