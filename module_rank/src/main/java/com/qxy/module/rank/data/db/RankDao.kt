package com.qxy.module.rank.data.db

import androidx.room.Dao
import com.qxy.lib.base.base.db.BaseDao
import com.qxy.module.rank.data.model.RankItem

@Dao
interface RankDao : BaseDao<RankItem> {
}