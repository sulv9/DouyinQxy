package com.qxy.lib.account.di

import com.qxy.lib.account.repo.AccountRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AccountRepoPoint {
    fun getAccountRepo(): AccountRepository
}