package com.qxy.module.personal.data.db

import androidx.room.Dao
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.personal.data.model.PersonalFan

@Dao
interface PersonalFanDao : BaseDao<PersonalFan> {
}