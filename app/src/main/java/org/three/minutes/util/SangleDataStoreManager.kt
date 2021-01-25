package org.three.minutes.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SangleDataStoreManager(val context: Context) {
    private val dataStore = context.createDataStore(name = "sangleDataStore")

    companion object {
        val deviceTokenKey = preferencesKey<String>("deviceToken")
        val tokenKey = preferencesKey<String>("token")
        val refreshTokenKey = preferencesKey<String>("refreshToken")
        val isNotificationKey = preferencesKey<Boolean>("notification")
        val isMotiveKey = preferencesKey<Boolean>("motive")
    }

    val deviceToken: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[deviceTokenKey] ?: ""
        }

    val token: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[tokenKey] ?: ""
        }

    val refreshToken: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[refreshTokenKey] ?: ""
        }

    val isNotification: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[isNotificationKey] ?: true
        }

    val isMotive: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[isMotiveKey] ?: true
        }

    suspend fun setDeviceToken(deviceToken: String) {
        dataStore.edit { preferences ->
            preferences[deviceTokenKey] = deviceToken
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[refreshTokenKey] = refreshToken
        }
    }

    suspend fun setReTokens(token: String, refresh: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
            preferences[refreshTokenKey] = refresh
        }
    }

    suspend fun setEnableAllPush() {
        dataStore.edit { preferences ->
            preferences[isNotificationKey] = true
            preferences[isMotiveKey] = true
        }
    }

    suspend fun setEnableNotificationPush() {
        dataStore.edit { preferences ->
            preferences[isNotificationKey] = true
        }
    }

    suspend fun setEnableMotivePush() {
        dataStore.edit { preferences ->
            preferences[isMotiveKey] = true

        }
    }

    suspend fun setDisableAllPush() {
        dataStore.edit { preferences ->
            preferences[isNotificationKey] = false
            preferences[isMotiveKey] = false
        }
    }

    suspend fun setDisableNotificationPush() {
        dataStore.edit { preferences ->
            preferences[isNotificationKey] = false
        }
    }

    suspend fun setDisableMotivePush() {
        dataStore.edit { preferences ->
            preferences[isMotiveKey] = false

        }
    }

}