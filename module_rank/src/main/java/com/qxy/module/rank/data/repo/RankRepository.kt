package com.qxy.module.rank.data.repo

import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.module.rank.data.api.RankService
import com.qxy.module.rank.data.db.RankDao
import javax.inject.Inject

class RankRepository @Inject constructor(
    remoteDataSource: RankRemoteDataSource,
    localDataSource: RankLocalDataSource
) : BaseRepositoryBoth<RankRemoteDataSource, RankLocalDataSource>(
    remoteDataSource,
    localDataSource
) {
}

class RankRemoteDataSource @Inject constructor(
    private val rankService: RankService
) : IRemoteDataSource {

}

class RankLocalDataSource @Inject constructor(
    private val rankDao: RankDao
) : ILocalDataSource {

}