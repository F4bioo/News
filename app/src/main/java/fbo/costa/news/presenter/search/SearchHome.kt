package fbo.costa.news.presenter.search

interface SearchHome {

    interface Presenter {

        fun onSearch(query: String)

        fun onSuccess()

        fun onError(message: String)

        fun onComplete()
    }
}
