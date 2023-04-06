package com.rikkimikki.mailnews.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
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
        initSearchView()

        savedInstanceState?:let { viewModel.getArticles() } //when you flip the screen, you do not need to make another request
    }

    private fun initObservers() {
        viewModel.articles.observe(this){
            adapter.addArticles(it)
            binding.loadDataProgressBar.visibility = View.GONE
        }
        viewModel.errors.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            binding.loadDataProgressBar.visibility = View.GONE
        }
    }

    private fun initAdapter(){
        adapter = ArticleAdapter()
        binding.ArticleRV.layoutManager = LinearLayoutManager(this)
        binding.ArticleRV.adapter = adapter
        adapter.onArticleClickListener = object : ArticleAdapter.OnArticleClickListener{
            override fun onClick(article: Article) {
                Toast.makeText(this@MainActivity, getString(R.string.article_click), Toast.LENGTH_SHORT).show()
            }
        }
        adapter.onReachEndListener = object : ArticleAdapter.OnReachEndListener{
            override fun onReachEnd() {
                viewModel.getArticles()
            }
        }
    }

    private fun initSearchView() {
        val sv = binding.ArticleSV
        sv.isIconified = false
        sv.clearFocus()

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    adapter.submitList(null)
                    binding.loadDataProgressBar.visibility = View.VISIBLE
                    viewModel.deselect()
                    viewModel.searchRequest = p0
                    viewModel.getArticles()
                    return true
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null && p0.isBlank())
                    adapter.submitList(null)
                return false
            }
        })
    }
}