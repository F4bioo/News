package fbo.costa.news.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceApiEntity?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
