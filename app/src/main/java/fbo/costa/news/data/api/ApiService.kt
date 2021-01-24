package fbo.costa.news.data.api

import fbo.costa.news.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        localeCode: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        appKey: String = BuildConfig.API_KEY
    ): Response<DataResponse>

    @GET("/v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        appKey: String = BuildConfig.API_KEY
    ): Response<DataResponse>
}
