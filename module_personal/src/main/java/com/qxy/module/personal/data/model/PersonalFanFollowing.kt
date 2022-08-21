package com.qxy.module.personal.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.qxy.lib.base.util.fromJsonList
import com.qxy.lib.base.util.toJsonList
import com.qxy.lib.common.network.ApiStatus

@Entity(tableName = "personal_fan_following")
@TypeConverters(PersonalFanFollowingListConverter::class)
data class PersonalFanFollowing(
    val total: Int?,
    @PrimaryKey
    val cursor: Long = Long.MAX_VALUE, // 下一页的Cursor
    @SerializedName("has_more")
    @ColumnInfo(name = "has_more")
    val hasMore: Boolean?,
    val list: List<PersonalFanFollowingItem>?,
    val type: Int = -1,
) : ApiStatus()

data class PersonalFanFollowingItem(
    @SerializedName("open_id")
    val openId: Int,
    @SerializedName("union_id")
    val unionId: Int,
    @SerializedName("nickname")
    val nickName: String,
    val avatar: String,
    val city: String,
    val province: String,
    val country: String,
    val gender: Int,
)

class PersonalFanFollowingListConverter {
    @TypeConverter
    fun fanListToString(data: List<PersonalFanFollowingItem>?): String = data?.toJsonList() ?: ""

    @TypeConverter
    fun stringToFanList(value: String): List<PersonalFanFollowingItem>? = value.fromJsonList()
}