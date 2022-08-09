package com.qxy.module.rank.data.api

import com.qxy.lib.common.network.ApiResponse
import com.qxy.module.rank.data.model.RankData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RankService {

    @Headers("Content-Type: application/json")
    @GET("discovery/ent/rank/item")
    suspend fun getRankItem(
        @Header("access-token") token: String,
        @Query("type") type: Int,
        @Query("version") version: Int? = null
    ) : ApiResponse<RankData>

}