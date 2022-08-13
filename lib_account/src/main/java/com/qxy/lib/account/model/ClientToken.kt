package com.qxy.lib.account.model

import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

data class ClientToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    val responseTime: Long = 0L,
): ApiStatus()