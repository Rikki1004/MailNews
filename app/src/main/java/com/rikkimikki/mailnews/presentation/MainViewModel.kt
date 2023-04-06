package com.rikkimikki.mailnews.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikkimikki.mailnews.data.ApiFactory
import com.rikkimikki.mailnews.domain.pojo.Article
import com.rikkimikki.mailnews.domain.pojo.ArticleResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    private val apiService = ApiFactory.getApiService()

    val articles = MutableLiveData<List<Article>>()
    val errors = MutableLiveData<String>()
    private var page = 1

    fun getArticles() {
        disposable.add(apiService.getArticles(page = page++)
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                articles.postValue(it.data)
            }) { throwable ->
                throwable.printStackTrace()
                errors.postValue(throwable.toString())
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}