package com.rikkimikki.mailnews.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rikkimikki.mailnews.R
import com.rikkimikki.mailnews.databinding.ArticleItemBinding
import com.rikkimikki.mailnews.domain.pojo.Article
import com.squareup.picasso.Picasso

class ArticleAdapter: ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback){

    var onArticleClickListener: OnArticleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]
        with(holder.viewBinding){
            title.text = article.title
            author.text = article.author
            description.text = article.description
            content.text = article.content
            publishedAt.text = article.publishedAt
            Picasso.get().load(article.urlToImage).into(image)

            next.setOnClickListener {
                onArticleClickListener?.onClick(article)
            }
        }
    }

    inner class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var viewBinding = ArticleItemBinding.bind(itemView)
    }

    interface OnArticleClickListener {
        fun onClick(article:Article)
    }
}