package org.three.minutes.architect.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.three.minutes.architect.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserUseCase @Inject constructor(
    private val userRepo: UserRepository
) {

    @ExperimentalCoroutinesApi
    suspend fun getBlockedUserList() = callbackFlow {
        kotlin.runCatching {
            userRepo.getBlockedUserList()
        }.onSuccess {
            trySend(it)
        }.onFailure {
            close(it)
        }.also {
            awaitClose()
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun postBlockUser(userIdx: Int) = callbackFlow {
        kotlin.runCatching {
            userRepo.postBlockUser(userIdx)
        }.onSuccess {
            trySend(it)
        }.onFailure {
            close(it)
        }.also {
            awaitClose()
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun deleteBlockUser(userIdx: Int) = callbackFlow {
        kotlin.runCatching {
            userRepo.deleteBlockUser(userIdx)
        }.onSuccess {
            trySend(it)
        }.onFailure {
            close(it)
        }.also {
            awaitClose()
        }
    }
}