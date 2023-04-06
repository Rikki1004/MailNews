package com.rikkimikki.mailnews.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rikkimikki.mailnews.R
import com.rikkimikki.mailnews.databinding.ArticleItemBinding
import com.rikkimikki.mailnews.domain.pojo.Article
import com.rikkimikki.mailnews.utils.convertDate

class ArticleAdapter: ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback){
    var onArticleClickListener: OnArticleClickListener? = null
    var onReachEndListener: OnReachEndListener? = null
    fun addArticles(articles: List<Article>){
        val newList = currentList.toMutableList()
        newList.addAll(articles)
        submitList(newList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]
        with(holder.viewBinding){
            title.text = article.title
            description.text = article.description
            date.text = convertDate(article.publishedAt)

            if (position > currentList.size - 2 && onReachEndListener != null) {
                onReachEndListener?.onReachEnd()
            } //automatic data upload
        }
    }

    inner class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var viewBinding = ArticleItemBinding.bind(itemView)
    }

    interface OnArticleClickListener {
        fun onClick(article:Article)
    }
    interface OnReachEndListener {
        fun onReachEnd()
    }
}