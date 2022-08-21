package com.qxy.module.personal.data.db

import androidx.room.Dao
import androidx.room.Query
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.personal.data.model.PersonalFanFollowing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface PersonalFanDao : BaseDao<PersonalFanFollowing> {
    @Query("SELECT * FROM personal_fan_following WHERE type = 1")
    fun observePersonalFanList(): Flow<List<PersonalFanFollowing>>

    fun observePersonalFanListUntilChanged() =
        observePersonalFanList().distinctUntilChanged()

    @Query("SELECT * FROM personal_fan_following WHERE type = 1 AND cursor = (SELECT MAX(cursor) FROM personal_fan_following)")
    suspend fun getNextPersonalFan(): PersonalFanFollowing?

    @Query("DELETE FROM personal_fan_following WHERE type = 1")
    suspend fun clearPersonalFan()
}