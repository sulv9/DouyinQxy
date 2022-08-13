package com.qxy.lib.account.model

import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/09 009 15:35
 */
data class AccessToken(
    @SerializedName(value = "access_token")
    val accessToken: String,

    @SerializedName(value = "expires_in")
    val expiresIn: Long,

    @SerializedName(value = "open_id")
    val openID: String,

    @SerializedName(value = "refresh_expires_in")
    val refreshExpiresIn: Long,

    @SerializedName(value = "refresh_token")
    val refreshToken: String,

    val scope: String,
    val responseTime: Long = 0L,
) : ApiStatus()