package com.qxy.lib.account.model

import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/13 013 22:47
 */
data class RefreshToken(
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val responseTime: Long = 0L,
) : ApiStatus()
