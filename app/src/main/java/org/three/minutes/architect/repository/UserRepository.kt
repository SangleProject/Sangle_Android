package org.three.minutes.architect.repository

import org.three.minutes.architect.data.ResponseBlockedUser

interface UserRepository {
    suspend fun getBlockedUserList(): List<ResponseBlockedUser>
}