package fbo.costa.news.presenter.main

interface MainHome {

    interface Presenter {

        fun onRequestList()

        fun onSuccess()

        fun onError(message: String)

        fun onComplete()
    }
}
