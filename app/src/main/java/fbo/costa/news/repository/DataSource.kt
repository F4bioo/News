package fbo.costa.news.repository

import android.content.Context
import fbo.costa.news.R
import fbo.costa.news.data.api.RetrofitInstance
import fbo.costa.news.data.model.Article
import fbo.costa.news.data.room.AppDatabase
import fbo.costa.news.data.room.DataRoom
import fbo.costa.news.presenter.favorite.FavoriteHome
import fbo.costa.news.presenter.main.MainHome
import fbo.costa.news.presenter.search.SearchHome
import fbo.costa.news.util.Constants
import fbo.costa.news.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataSource(private var context: Context) {

    private var db = AppDatabase.getInstance(context)
    private var dataRoom = DataRoom(db)

    fun getBreakingNews(callback: MainHome.Presenter) {
        if (NetworkUtil().isOnline(context)) {
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
        } else {
            callback.onError(context.getString(R.string.text_no_internet_connection))
            callback.onComplete()
        }
    }

    fun getSearchNews(searchQuery: String, callback: SearchHome.Presenter) {
        if (NetworkUtil().isOnline(context)) {
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
        } else {
            callback.onError(context.getString(R.string.text_no_internet_connection))
            callback.onComplete()
        }
    }

    fun insertModel(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            dataRoom.insertModel(article)
        }
    }

    fun selectList(callback: FavoriteHome.Presenter) {
        var articleList: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            articleList = dataRoom.selectList()

            withContext(Dispatchers.Main) {
                callback.onSuccess(articleList)
            }
        }
    }

    fun deleteModel(article: Article?) {
        CoroutineScope(Dispatchers.Main).launch {
            article?.let { _article ->
                dataRoom.deleteModel(_article)
            }
        }
    }
}
