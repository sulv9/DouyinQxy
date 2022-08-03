package com.qxy.lib.account.di

import com.qxy.api.account.IAccountService
import com.qxy.lib.account.service.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun provideAccountService(service: AccountServiceImpl): IAccountService

}