package com.qxy.module.personal.data.model

import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/20 020 17:45
 */
data class PersonalInfo(
    val avatar: String,
    val city: String,
    val country: String,

    @SerializedName("e_account_role")
    val eAccountRole: String,

    val gender: String,
    val nickname: String,

    @SerializedName("open_id")
    val openID: String,

    val province: String,

    @SerializedName("union_id")
    val unionID: String
): ApiStatus()
