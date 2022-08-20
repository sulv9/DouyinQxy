package com.qxy.module.rank.data.db

import androidx.room.Dao
import androidx.room.Query
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.rank.data.model.RankVersion
import kotlinx.coroutines.flow.Flow

@Dao
interface RankVersionDao : BaseDao<RankVersion> {

    @Query("SELECT * FROM rank_version WHERE type = :type")
    fun observeRankVersionList(type: Int): Flow<List<RankVersion>>

    @Query("SELECT * FROM rank_version WHERE type = :type AND cursor = (SELECT MAX(cursor) FROM rank_version)")
    suspend fun getNextRankVersion(type: Int): RankVersion?

    @Query("DELETE FROM rank_version")
    suspend fun clearRankVersion()

}