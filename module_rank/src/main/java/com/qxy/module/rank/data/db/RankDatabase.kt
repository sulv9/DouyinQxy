package com.qxy.module.rank.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.model.RankVersion

@Database(entities = [RankItem::class, RankVersion::class], version = 6)
abstract class RankDatabase : RoomDatabase() {
    abstract fun rankDao(): RankDao
    abstract fun rankVersionDao(): RankVersionDao

    companion object {
        private const val DATABASE_NAME = "rank-db"

        @Volatile
        private var INSTANCE: RankDatabase? = null

        fun getInstance(context: Context): RankDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRankDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildRankDatabase(context: Context): RankDatabase {
            return Room.databaseBuilder(context, RankDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration().build()
        }
    }
}