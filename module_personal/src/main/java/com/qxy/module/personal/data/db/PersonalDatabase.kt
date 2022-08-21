package com.qxy.module.personal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qxy.module.personal.data.model.PersonalFanFollowing
import com.qxy.module.personal.data.model.PersonalInfo

@Database(entities = [PersonalInfo::class, PersonalFanFollowing::class], version = 1)
abstract class PersonalDatabase : RoomDatabase() {
    abstract fun fanDao(): PersonalFanDao
    abstract fun followingDao(): PersonalFollowingDao

    companion object {
        private const val DATABASE_NAME = "personal-db"

        @Volatile
        private var INSTANCE: PersonalDatabase? = null

        fun getInstance(context: Context): PersonalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRankDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildRankDatabase(context: Context): PersonalDatabase {
            return Room.databaseBuilder(context, PersonalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration().build()
        }
    }
}