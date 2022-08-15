package com.qxy.module.rank.data.db

import androidx.room.Dao
import androidx.room.Query
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.rank.data.model.RankVersionItem

@Dao
interface RankVersionDao: BaseDao<RankVersionItem> {
    @Query("SELECT * FROM rank_version_item WHERE type = :type AND version = :version")
    suspend fun getRankVersionData(type: Int, version: Int): List<RankVersionItem>

    @Query("DELETE FROM rank_version_item")
    suspend fun clearRankVersionData()
}