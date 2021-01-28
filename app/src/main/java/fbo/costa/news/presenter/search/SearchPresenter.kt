package fbo.costa.news.presenter.search

import fbo.costa.news.data.api.DataResponse
import fbo.costa.news.data.model.Article
import fbo.costa.news.data.model.ArticleApiEntity
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.repository.DataSource

class SearchPresenter(
    private val view: ViewHome.View,
    private val dataSource: DataSource
) : SearchHome.Presenter {

    override fun onSearch(query: String) {
        this.view.showProgress()
        this.dataSource.getSearchNews(query, this)
    }

    override fun onSuccess(dataResponse: DataResponse) {
        val articleList = arrayListOf<Article>()

        dataResponse.articles.forEach { _articleApiEntity ->
            articleList.add(_articleApiEntity.toArticle())
        }
        this.view.showList(articleList)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgress()
    }

    // Convert ArticleApiEntity to Article
    private fun ArticleApiEntity.toArticle(): Article {
        return Article(
            author = this.author,
            content = this.content,
            description = this.description,
            publishedAt = this.publishedAt,
            source = this.source,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )
    }
}
