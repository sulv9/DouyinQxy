package com.qxy.module.personal.data.repo

import androidx.core.content.edit
import androidx.room.withTransaction
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.processResults
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.ext.toast
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.processApiResponse
import com.qxy.module.personal.KEY_PERSONAL_INFO
import com.qxy.module.personal.PAGE_SIZE
import com.qxy.module.personal.data.api.PersonalFanFollowingService
import com.qxy.module.personal.data.model.PersonalInfo
import com.qxy.module.personal.data.api.PersonalService
import com.qxy.module.personal.data.model.PersonalVideo
import com.qxy.module.personal.data.db.PersonalDatabase
import com.qxy.module.personal.data.model.PersonalFanFollowing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
    fun getPersonalInfo(): Flow<Results<PersonalInfo>> {
        val localFlow = flow {
            localDataSource.localPersonalInfo?.let {
                val localData = processResults { it }
                emit(localData)
            } ?: emit(Results.None)
        }
        val remoteFlow = flow {
            val accessToken = remoteDataSource.getAccessToken()
            val remoteData = processResults {
                remoteDataSource.getRemotePersonalInfo(accessToken, remoteDataSource.getOpenId())
                    .also {
                        localDataSource.localPersonalInfo = it
                    }
            }
            emit(remoteData)
        }
        return flowOf(localFlow, remoteFlow)
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    fun getPersonalVideo(cursor: Int, count: Int): Flow<Results<PersonalVideo>> {
        return flow {
            val result = processResults {
                remoteDataSource.getRemotePersonalVideo(
                    remoteDataSource.getOpenId(),
                    cursor, count,
                    remoteDataSource.getAccessToken()
                )
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNewestFollowingCursor() = localDataSource.fetchNextFollowing()?.cursor ?: 0

    suspend fun getNewestFanCursor() = localDataSource.fetchNextFan()?.cursor ?: 0

    suspend fun fetchFollowingData(cursor: Long, isRefresh: Boolean) {
        if (cursor == Long.MAX_VALUE) return
        val remoteResult = processResults {
            remoteDataSource.getRemoteFollowingList(cursor, PAGE_SIZE)
        }
        if (remoteResult is Results.Success) {
            if (isRefresh)
                localDataSource.clearAndInsertNewFollowingData(remoteResult.value)
            else
                localDataSource.insertNewFollowingData(remoteResult.value)
        } else if (remoteResult is Results.Failure) {
            toast("${remoteResult.errors.message}")
        }
    }

    suspend fun fetchFanData(cursor: Long, isRefresh: Boolean) {
        if (cursor == Long.MAX_VALUE) return
        val remoteResult = processResults {
            remoteDataSource.getRemoteFanList(cursor, PAGE_SIZE)
        }
        if (remoteResult is Results.Success) {
            if (isRefresh)
                localDataSource.clearAndInsertNewFanData(remoteResult.value)
            else
                localDataSource.insertNewFanData(remoteResult.value)
        } else if (remoteResult is Results.Failure) {
            toast("${remoteResult.errors.message}")
        }
    }

    fun getFollowingList() =
        localDataSource
            .getFollowingStream()
            .map { processResults { it } }
            .flowOn(Dispatchers.IO)

    fun getFanList() =
        localDataSource
            .getFanStream()
            .map { processResults { it } }
            .flowOn(Dispatchers.IO)
}

class PersonalLocalDataSource @Inject constructor(
    private val db: PersonalDatabase,
) : ILocalDataSource {

    private val sp = ARouterUtil.getService(IAccountService::class).sharedPref

    @set:JvmName("saveLocalPersonalInfo")
    var localPersonalInfo: PersonalInfo?
        get() {
            return sp.getString(KEY_PERSONAL_INFO, null)?.fromJson()
        }
        set(value) {
            sp.edit {
                putString(KEY_PERSONAL_INFO, value!!.toJson())
            }
        }

    suspend fun clearAndInsertNewFollowingData(followingData: PersonalFanFollowing) {
        db.withTransaction {
            with(db.followingDao()) {
                clearPersonalFollowing()
                insert(followingData)
            }
        }
    }

    suspend fun clearAndInsertNewFanData(fanData: PersonalFanFollowing) {
        db.withTransaction {
            with(db.fanDao()) {
                clearPersonalFan()
                insert(fanData)
            }
        }
    }

    suspend fun insertNewFollowingData(followingData: PersonalFanFollowing) {
        db.withTransaction {
            db.followingDao().insert(followingData)
        }
    }

    suspend fun insertNewFanData(fanData: PersonalFanFollowing) {
        db.withTransaction {
            db.fanDao().insert(fanData)
        }
    }

    suspend fun fetchNextFollowing(): PersonalFanFollowing? {
        return db.withTransaction {
            db.followingDao().getNextPersonalFollowing()
        }
    }

    suspend fun fetchNextFan(): PersonalFanFollowing? {
        return db.withTransaction {
            db.fanDao().getNextPersonalFan()
        }
    }

    fun getFollowingStream() = db.followingDao().observePersonalFollowingListUntilChanged()

    fun getFanStream() = db.fanDao().observePersonalFanListUntilChanged()
}

class PersonalRemoteDataSource @Inject constructor(
    private val personalService: PersonalService,
    private val fanFollowingService: PersonalFanFollowingService,
) : IRemoteDataSource {
    private val service = ARouterUtil.getService(IAccountService::class)

    suspend fun getRemotePersonalInfo(accessToken: String, openID: String): PersonalInfo {
        return processApiResponse {
            personalService.getPersonalInfo(accessToken, openID)
        }
    }

    suspend fun getRemotePersonalVideo(
        openID: String,
        cursor: Int,
        count: Int,
        accessToken: String
    ) = processApiResponse {
        personalService.getPersonalVideo(openID, cursor, count, accessToken)
    }

    suspend fun getAccessToken() = service.getAccessToken()

    fun getOpenId() = service.openID

    suspend fun getRemoteFollowingList(cursor: Long, count: Int): PersonalFanFollowing {
        return processApiResponse {
            fanFollowingService.getFollowingList(getAccessToken(), getOpenId(), cursor, count)
        }.copy(type = 0)
    }

    suspend fun getRemoteFanList(cursor: Long, count: Int): PersonalFanFollowing {
        return processApiResponse {
            fanFollowingService.getFanList(getAccessToken(), getOpenId(), cursor, count)
        }.copy(type = 1)
    }
}