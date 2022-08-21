package com.qxy.module.personal.service

import com.qxy.lib.common.network.ApiResponse
import com.qxy.module.personal.model.PersonalInfo
import retrofit2.http.*


/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/20 020 17:56
 */
interface PersonalService {
    @POST("oauth/userinfo")
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    suspend fun getPersonalInfo(
        @Field("access_token") accessToken: String,
        @Field("open_id") openID: String,
        @Header("access-token") accessTokenHeader: String = accessToken
    ): ApiResponse<PersonalInfo>
}