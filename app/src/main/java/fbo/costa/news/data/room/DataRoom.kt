package fbo.costa.news.data.room

import fbo.costa.news.data.model.Article
import fbo.costa.news.data.model.ArticleRoomEntity

class DataRoom(private val db: AppDatabase) {

    suspend fun insertModel(article: Article): Long {
        return db.articleDao().insert(article.toArticleRoomEntity())
    }

    suspend fun selectList(): List<Article> {
        val articleRoomList = db.articleDao().select()
        val articleList = arrayListOf<Article>()
        articleRoomList.forEach { _articleRoomEntity ->
            articleList.add(_articleRoomEntity.toArticle())
        }
        return articleList
    }

    suspend fun deleteModel(article: Article) {
        db.articleDao().delete(article.toArticleRoomEntity())
    }

    // Convert Article to ArticleRoomEntity for database INSERT
    private fun Article.toArticleRoomEntity(): ArticleRoomEntity {
        return ArticleRoomEntity(
            id = this.id,
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

    // Convert ArticleRoomEntity to Article for database SELECT
    private fun ArticleRoomEntity.toArticle(): Article {
        return Article(
            id = this.id,
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
