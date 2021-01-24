package fbo.costa.news.data.model

import java.io.Serializable

data class Article(
    val id: Long = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceApiEntity?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
