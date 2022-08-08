package com.qxy.module.rank.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.module.rank.data.model.RankItem

@Database(entities = [RankItem::class], version = 1)
abstract class RankDatabase : RoomDatabase() {
    abstract fun rankDao(): RankDao

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
            return Room.databaseBuilder(context, RankDatabase::class.java, DATABASE_NAME).build()
        }
    }
}