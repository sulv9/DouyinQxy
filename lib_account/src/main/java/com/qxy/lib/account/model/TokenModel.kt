package com.qxy.lib.account.model

import com.google.gson.annotations.SerializedName

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/09 009 15:35
 */
data class TokenModel(
    @SerializedName(value = "access_token")
    val accessToken: String,

    val description: String,

    @SerializedName(value = "error_code")
    val errorCode: Int,

    @SerializedName(value = "expires_in")
    val expiresIn: Int,

    @SerializedName(value = "open_id")
    val openID: String,

    @SerializedName(value = "refresh_expires_in")
    val refreshExpiresIn: Int,

    @SerializedName(value = "refresh_token")
    val refreshToken: String,

    val scope: String
)