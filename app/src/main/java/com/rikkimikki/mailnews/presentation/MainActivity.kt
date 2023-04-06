package com.rikkimikki.mailnews.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikkimikki.mailnews.R
import com.rikkimikki.mailnews.databinding.ActivityMainBinding
import com.rikkimikki.mailnews.domain.pojo.Article
import com.rikkimikki.mailnews.presentation.adapters.ArticleAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initAdapter()
        initObservers()

        viewModel.getArticles()
    }

    private fun initObservers() {
        viewModel.articles.observe(this){
            adapter.submitList(it.toMutableList())
        }
        viewModel.errors.observe(this){
            println(it)
        }
    }

    private fun initAdapter(){
        adapter = ArticleAdapter()
        binding.ArticleRV.layoutManager = LinearLayoutManager(this)
        binding.ArticleRV.adapter = adapter
        adapter.onArticleClickListener = object : ArticleAdapter.OnArticleClickListener{
            override fun onClick(article: Article) {

            }
        }
    }
}