package com.qxy.module.rank.di

import com.qxy.module.rank.data.api.RankService
import com.qxy.module.rank.data.api.RankVersionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideRankService(retrofit: Retrofit): RankService {
        return retrofit.create(RankService::class.java)
    }

    @Provides
    @Singleton
    fun provideRankVersionService(retrofit: Retrofit): RankVersionService {
        return retrofit.create(RankVersionService::class.java)
    }

}