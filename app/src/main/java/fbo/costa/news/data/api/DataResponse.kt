package fbo.costa.news.data.api

import fbo.costa.news.data.model.ArticleApiEntity

data class DataResponse(
    val articles: ArrayList<ArticleApiEntity>,
    val status: String,
    val totalResults: Int
)
