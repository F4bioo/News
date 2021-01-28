package fbo.costa.news.presenter

import fbo.costa.news.data.model.Article

interface ViewHome {

    interface View {

        fun showProgress()

        fun showFailure(message: String)

        fun hideProgress()

        fun showList(articleList: List<Article>)

    }

    interface Favorite {

        fun showList(articleList: List<Article>)

    }
}
