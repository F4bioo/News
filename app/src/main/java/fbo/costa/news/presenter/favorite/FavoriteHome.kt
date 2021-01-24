package fbo.costa.news.presenter.favorite

import fbo.costa.news.data.model.Article

interface FavoriteHome {

    interface Presenter {

        fun onSuccess(articleList: List<Article>)

    }
}
