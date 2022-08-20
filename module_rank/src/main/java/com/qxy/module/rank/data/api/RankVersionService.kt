package com.qxy.module.rank.data.api

import com.qxy.lib.common.network.ApiResponse
import com.qxy.module.rank.data.model.RankVersion
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RankVersionService {

    @Headers("Content-Type: application/json")
    @GET("discovery/ent/rank/version")
    suspend fun getRankVersions(
        @Header("access-token") accessToken: String,
        @Query("cursor") cursor: Int = 0,
        @Query("count") count: Int,
        @Query("type") type: Int
    ): ApiResponse<RankVersion>

}