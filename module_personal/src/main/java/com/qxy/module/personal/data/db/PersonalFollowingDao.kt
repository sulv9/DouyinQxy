package com.qxy.module.personal.data.db

import androidx.room.Dao
import androidx.room.Query
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.personal.data.model.PersonalFanFollowing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface PersonalFollowingDao : BaseDao<PersonalFanFollowing> {
    @Query("SELECT * FROM personal_fan_following WHERE type = 0")
    fun observePersonalFollowingList(): Flow<List<PersonalFanFollowing>>

    fun observePersonalFollowingListUntilChanged() =
        observePersonalFollowingList().distinctUntilChanged()

    @Query("SELECT * FROM personal_fan_following WHERE type = 0 AND cursor = (SELECT MAX(cursor) FROM personal_fan_following)")
    suspend fun getNextPersonalFollowing(): PersonalFanFollowing?

    @Query("DELETE FROM personal_fan_following WHERE type = 0")
    suspend fun clearPersonalFollowing()
}