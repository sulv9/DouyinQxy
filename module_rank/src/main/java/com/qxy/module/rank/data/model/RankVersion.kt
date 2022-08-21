package com.qxy.module.rank.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.qxy.lib.base.util.fromJsonList
import com.qxy.lib.base.util.toJsonList
import com.qxy.lib.common.network.ApiStatus

@Entity(tableName = "rank_version", primaryKeys = ["type", "cursor"])
@TypeConverters(RankVersionListConverter::class)
data class RankVersion(
    val cursor: Int = Integer.MAX_VALUE, // 下一页的Cursor
    @SerializedName("has_more")
    @ColumnInfo(name = "has_more")
    val hasMore: Boolean?,
    @SerializedName("list")
    @ColumnInfo(name = "list")
    val versionList: List<RankVersionItem>?,
    val type: Int = -1,
): ApiStatus()

data class RankVersionItem(
    @SerializedName("active_time")
    val activeTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("start_time")
    val startTime: String,
    val type: Int,
    val version: Int,
) {
    companion object {
        fun empty() = RankVersionItem(
            "",
            "",
            "",
            -1,
            -1,
        )
    }
}

class RankVersionListConverter {
    @TypeConverter
    fun versionListToString(data: List<RankVersionItem>?): String = data?.toJsonList() ?: ""

    @TypeConverter
    fun stringToVersionList(value: String): List<RankVersionItem>? = value.fromJsonList()
}