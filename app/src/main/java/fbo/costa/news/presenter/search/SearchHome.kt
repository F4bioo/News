package fbo.costa.news.presenter.search

import fbo.costa.news.data.api.DataResponse

interface SearchHome {

    interface Presenter {

        fun onSearch(query: String)

        fun onSuccess(dataResponse: DataResponse)

        fun onError(message: String)

        fun onComplete()
    }
}
