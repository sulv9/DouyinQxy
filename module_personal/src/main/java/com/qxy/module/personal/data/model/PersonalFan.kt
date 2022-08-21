package com.qxy.module.personal.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.qxy.lib.base.util.fromJsonList
import com.qxy.lib.base.util.toJsonList
import com.qxy.lib.common.network.ApiStatus

@Entity(tableName = "personal_fan")
@TypeConverters(PersonalFanListConverter::class)
data class PersonalFan(
    val total: Int,
    @PrimaryKey
    val cursor: Int = Integer.MAX_VALUE, // 下一页的Cursor
    @SerializedName("has_more")
    @ColumnInfo(name = "has_more")
    val hasMore: Boolean?,
    @SerializedName("list")
    @ColumnInfo(name = "list")
    val versionList: List<PersonalFanItem>?,
) : ApiStatus()

data class PersonalFanItem(
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

class PersonalFanListConverter {
    @TypeConverter
    fun fanListToString(data: List<PersonalFanItem>?): String = data?.toJsonList() ?: ""

    @TypeConverter
    fun stringToFanList(value: String): List<PersonalFanItem>? = value.fromJsonList()
}