package org.three.minutes.util

import android.content.Context
import android.util.Log
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
        val isOnBoardingKey = preferencesKey<Boolean>("onBoarding")
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

    val isOnBoarding: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[isOnBoardingKey] ?: true
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

    suspend fun setNotificationPush(check : Boolean) {
        dataStore.edit { preferences ->
            preferences[isNotificationKey] = check
        }
    }

    suspend fun setMotivePush(check : Boolean) {
        dataStore.edit { preferences ->
            preferences[isMotiveKey] = check
        }
    }

    suspend fun setOnBoarding(check : Boolean) {
        dataStore.edit { preferences ->
            preferences[isOnBoardingKey] = check
        }
    }

}