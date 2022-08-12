package com.qxy.lib.account.network

import com.qxy.lib.account.model.TokenModel
import com.qxy.lib.common.network.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/09 009 15:16
 */
interface TokenService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/access_token")
    suspend fun getAccessToken(
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
        @Field("client_key") clientKey: String,
        @Field("grant_type") grantType: String = "authorization_code"
    ): ApiResponse<TokenModel>

}