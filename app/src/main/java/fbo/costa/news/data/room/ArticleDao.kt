package fbo.costa.news.data.room

import androidx.room.*
import fbo.costa.news.data.model.ArticleRoomEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleRoomEntity: ArticleRoomEntity): Long

    @Query("SELECT * FROM article")
    suspend fun select(): List<ArticleRoomEntity>

    @Delete()
    suspend fun delete(articleRoomEntity: ArticleRoomEntity)
}
