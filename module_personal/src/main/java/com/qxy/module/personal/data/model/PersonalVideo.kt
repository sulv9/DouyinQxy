package com.qxy.module.personal.data.model

import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/21 021 19:48
 */
data class PersonalVideo(
    @SerializedName("has_more")
    val hasMore: Boolean,
    val list: List<ListElement>,
    val cursor: Long
) : ApiStatus() {
    data class ListElement(
        val title: String,

        @SerializedName("is_top")
        val isTop: Boolean,

        @SerializedName("create_time")
        val createTime: Long,

        @SerializedName("is_reviewed")
        val isReviewed: Boolean,

        @SerializedName("video_status")
        val videoStatus: Long,

        @SerializedName("share_url")
        val shareURL: String,

        @SerializedName("item_id")
        val itemID: String,

        @SerializedName("video_id")
        val videoID: String,

        @SerializedName("media_type")
        val mediaType: Long,

        val cover: String,
        val statistics: Statistics
    )

    data class Statistics(
        @SerializedName("forward_count")
        val forwardCount: Long,

        @SerializedName("comment_count")
        val commentCount: Long,

        @SerializedName("digg_count")
        val diggCount: Long,

        @SerializedName("download_count")
        val downloadCount: Long,

        @SerializedName("play_count")
        val playCount: Long,

        @SerializedName("share_count")
        val shareCount: Long
    )
}