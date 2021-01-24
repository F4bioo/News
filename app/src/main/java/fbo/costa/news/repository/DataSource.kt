package fbo.costa.news.repository

import fbo.costa.news.data.api.RetrofitInstance
import fbo.costa.news.presenter.main.MainHome
import fbo.costa.news.presenter.search.SearchHome
import fbo.costa.news.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSource {

    fun getBreakingNews(callback: MainHome.Presenter) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = RetrofitInstance.api.getBreakingNews(Constants.LOCALE_CODE)
            if (response.isSuccessful) {
                response.body()?.let { _newsResponse ->
                    callback.onSuccess(_newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun getSearchNews(searchQuery: String, callback: SearchHome.Presenter) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = RetrofitInstance.api.getSearchNews(searchQuery)
            if (response.isSuccessful) {
                response.body()?.let { _newsResponse ->
                    callback.onSuccess(_newsResponse)
                }
                callback.onComplete()

            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }
}
