package com.qxy.module.personal.di

import com.qxy.module.personal.data.api.PersonalFanFollowingService
import com.qxy.module.personal.data.api.PersonalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/21 021 00:42
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providePersonalService(retrofit: Retrofit): PersonalService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providePersonalFanFollowingService(retrofit: Retrofit): PersonalFanFollowingService {
        return retrofit.create()
    }
}