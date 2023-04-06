package com.rikkimikki.mailnews.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikkimikki.mailnews.R
import com.rikkimikki.mailnews.data.ApiFactory
import com.rikkimikki.mailnews.domain.pojo.Article
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private val apiService = ApiFactory.getApiService()

    val articles = MutableLiveData<List<Article>>()
    val errors = MutableLiveData<String>()
    private var page = 1
    private var total = Int.MAX_VALUE
    var searchRequest = "Kotlin" //default request

    fun getArticles() {
        if (page * PAGE_SIZE < total){
            disposable.add(apiService.getArticles(page = page++,q = searchRequest)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    total = it.totalResults
                    articles.postValue(it.data)
                    if (it.data.isEmpty())
                        errors.postValue(getApplication<Application>().getString(R.string.nofing))
                }) { throwable ->
                    throwable.printStackTrace()
                    if (throwable.message != null && throwable.message == "HTTP 426 ") //strange error of the server itself
                        errors.postValue(getApplication<Application>().getString(R.string.page_end))
                    else
                        errors.postValue(throwable.toString())
                })
        } else {
            errors.value = getApplication<Application>().getString(R.string.page_end)
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun deselect() {
        page = 1
        total = Int.MAX_VALUE
    }

    companion object{
        const val PAGE_SIZE = 20
    }
}