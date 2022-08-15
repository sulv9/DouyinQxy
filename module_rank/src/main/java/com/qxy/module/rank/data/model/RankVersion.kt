package com.qxy.module.rank.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.qxy.lib.common.network.ApiStatus

data class RankVersion(
    val cursor: Long?,
    @SerializedName("has_more")
    val hasMore: Boolean?,
    @SerializedName("list")
    val versionList: List<RankVersionItem>,
): ApiStatus()

@Entity(tableName = "rank_version_item")
data class RankVersionItem(
    @SerializedName("active_time")
    @ColumnInfo(name = "active_time")
    val activeTime: String,
    @SerializedName("end_time")
    @ColumnInfo(name = "end_time")
    val endTime: String,
    @SerializedName("start_time")
    @ColumnInfo(name = "start_time")
    val startTime: String,
    val type: Int,
    @PrimaryKey
    val version: Int
)