package fbo.costa.news.presenter.favorite

import fbo.costa.news.data.model.Article
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.repository.DataSource

class FavoritePresenter(
    private val view: ViewHome.Favorite,
    private val dataSource: DataSource
) : FavoriteHome.Presenter {

    override fun onSuccess(articleList: List<Article>) {
        view.showList(articleList)
    }

    fun insertModel(article: Article) {
        this.dataSource.insertModel(article)
    }

    fun selectList() {
        this.dataSource.selectList(this)
    }

    fun deleteModel(article: Article) {
        this.dataSource.deleteModel(article)
    }
}
