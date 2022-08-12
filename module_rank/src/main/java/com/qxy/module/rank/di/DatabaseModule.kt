package com.qxy.module.rank.di

import android.content.Context
import com.qxy.module.rank.data.db.RankDao
import com.qxy.module.rank.data.db.RankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRankDatabase(@ApplicationContext context: Context): RankDatabase {
        return RankDatabase.getInstance(context)
    }

}