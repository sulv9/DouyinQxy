package com.qxy.module.personal.di

import android.content.Context
import com.qxy.module.personal.data.db.PersonalDatabase
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
    fun provideRankDatabase(@ApplicationContext context: Context): PersonalDatabase {
        return PersonalDatabase.getInstance(context)
    }

}