package fbo.costa.news.presenter.main

import fbo.costa.news.data.api.DataResponse

interface MainHome {

    interface Presenter {

        fun onRequestList()

        fun onSuccess(dataResponse: DataResponse)

        fun onError(message: String)

        fun onComplete()
    }
}
