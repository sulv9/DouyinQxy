package com.qxy.lib.account.di

import com.qxy.lib.account.network.PersonalService
import com.qxy.lib.account.network.TokenService
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
 * @time 2022/08/09 009 16:00
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideTokenService(retrofit: Retrofit): TokenService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providePersonalService(retrofit: Retrofit): PersonalService {
        return retrofit.create()
    }
}