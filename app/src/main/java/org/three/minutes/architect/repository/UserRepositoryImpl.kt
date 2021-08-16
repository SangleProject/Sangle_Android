package org.three.minutes.architect.repository

import kotlinx.coroutines.flow.first
import org.three.minutes.ThreeApplication
import org.three.minutes.architect.data.ResponseBlockedUser
import org.three.minutes.server.SangleService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val sangleService: SangleService
): UserRepository {

    override suspend fun getBlockedUserList(): List<ResponseBlockedUser> {
        val token = ThreeApplication.getInstance().getDataStore().token.first()
        return sangleService.getBlockedUserList(token)
    }
}