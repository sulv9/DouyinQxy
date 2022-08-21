package com.qxy.module.personal.data.api

import com.qxy.lib.common.network.ApiResponse
import com.qxy.module.personal.data.model.PersonalFanFollowing
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface PersonalFanFollowingService {

    @Headers("Content-Type: application/json")
    @GET("fans/list")
    suspend fun getFanList(
        @Header("access-token") accessToken: String,
        @Query("open_id") openId: String,
        @Query("cursor") cursor: Long = 0,
        @Query("count") count: Int,
    ): ApiResponse<PersonalFanFollowing>

    @Headers("Content-Type: application/json")
    @GET("following/list")
    suspend fun getFollowingList(
        @Header("access-token") accessToken: String,
        @Query("open_id") openId: String,
        @Query("cursor") cursor: Long = 0,
        @Query("count") count: Int,
    ) : ApiResponse<PersonalFanFollowing>

}