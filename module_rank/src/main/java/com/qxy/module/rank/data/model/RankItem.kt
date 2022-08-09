package com.qxy.module.rank.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.ApiStatus

data class RankData(
    @SerializedName("active_time")
    val activeTime: String, // 响应时间
    val rankList: List<RankItem>?, // rank列表
) : ApiStatus()

@Entity(tableName = "rank_item")
@TypeConverters(ListConverter::class)
data class RankItem(
    val actors: List<String>, // 演员
    val areas: List<String>, // 地区
    val directors: List<String>, // 导演
    @SerializedName("discussion_hot")
    @ColumnInfo(name = "discussion_hot")
    val discussionHot: Long, // 视频讨论度
    val hot: Long, // 热度值
    @PrimaryKey
    val id: String, // 	影片ID
    @SerializedName("influence_hot")
    @ColumnInfo(name = "influence_hot")
    val influenceHot: Long, // 	账号影响力
    @SerializedName("maoyan_id")
    @ColumnInfo(name = "maoyan_id")
    val maoYanId: String, // 猫眼id：只有电影榜返回，可能为空
    val name: String, // 片名
    @SerializedName("name_en")
    @ColumnInfo(name = "name_en")
    val nameEn: String, // 英文片名
    val poster: String, // 海报
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String, // 上映时间
    @SerializedName("search_hot")
    @ColumnInfo(name = "search_hot")
    val searchHot: Long, // 搜索指数
    val tags: List<String>, // 类型标签
    @SerializedName("topic_hot")
    @ColumnInfo(name = "topic_hot")
    val topicHot: Long, // 话题热度值
    val type: Int, // 类型：1=电影 2=电视剧 3=综艺
)

class ListConverter {
    @TypeConverter
    fun listToString(data: List<String>): String = data.toJson()

    @TypeConverter
    fun stringToList(value: String): List<String> = value.fromJson()
}
