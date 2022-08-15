package com.qxy.lib.account.network

import com.qxy.lib.account.model.ClientToken
import com.qxy.lib.account.model.AccessToken
import com.qxy.lib.account.model.RefreshToken
import com.qxy.lib.common.network.ApiResponse
import retrofit2.http.*

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
    ): ApiResponse<AccessToken>

    @FormUrlEncoded
    @POST("oauth/client_token")
    suspend fun getClientToken(
        @Field("client_key") clientKey: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "client_credential",
    ): ApiResponse<ClientToken>

    @FormUrlEncoded
    @Headers("Content-Type: multipart/form-data")
    @POST("oauth/renew_refresh_token")
    suspend fun getRefreshToken(
        @Field("client_key") clientKey: String,
        @Field("refresh_token") refreshToken: String
    ): ApiResponse<RefreshToken>

    @POST("oauth/refresh_token")
    @FormUrlEncoded
    suspend fun refreshAccessToken(
        @Field("client_key") clientKey: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): ApiResponse<AccessToken>
}