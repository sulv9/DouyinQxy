package com.qxy.module.rank.data.db

import androidx.room.Dao
import androidx.room.Query
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.rank.data.model.RankItem

@Dao
interface RankDao : BaseDao<RankItem> {
    @Query("SELECT * FROM rank_item WHERE type = :type AND version = :version")
    suspend fun getRankData(type: Int, version: Int): List<RankItem>

    @Query("DELETE FROM rank_item")
    suspend fun clearRankData()
}