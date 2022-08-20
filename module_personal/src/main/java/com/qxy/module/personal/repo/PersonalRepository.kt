package com.qxy.module.personal.repo

import android.content.Context
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/20 020 16:02
 */
class PersonalRepository @Inject constructor(
    remoteDataSource: PersonalRemoteDataSource,
    localDataSource: PersonalLocalDataSource
) : BaseRepositoryBoth<PersonalRemoteDataSource, PersonalLocalDataSource>(
    remoteDataSource,
    localDataSource
) {

}

class PersonalLocalDataSource @Inject constructor(

) : ILocalDataSource {

}

class PersonalRemoteDataSource @Inject constructor(

) : IRemoteDataSource {

}