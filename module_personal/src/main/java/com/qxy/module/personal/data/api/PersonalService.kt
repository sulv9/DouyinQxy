package com.qxy.module.personal.data.api

import com.qxy.lib.common.network.ApiResponse
import com.qxy.module.personal.data.model.PersonalInfo
import com.qxy.module.personal.data.model.PersonalVideo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/20 020 17:56
 */
interface PersonalService {
    @GET("oauth/userinfo")
    @Headers("Content-Type: application/json")
    suspend fun getPersonalInfo(
        @Query("access_token") accessToken: String,
        @Query("open_id") openID: String,
        @Header("access-token") accessTokenHeader: String = accessToken
    ): ApiResponse<PersonalInfo>

    @GET("video/list")
    @Headers("Content-Type: application/json")
    suspend fun getPersonalVideo(
        @Query("open_id") openID: String,
        @Query("cursor") cursor: Int,
        @Query("count") count: Int,
        @Header("access-token") accessToken: String
    ): ApiResponse<PersonalVideo>
}