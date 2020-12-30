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
import kotlin.concurrent.thread

class SangleDataStoreManager(val context: Context) {
    private val dataStore = context.createDataStore(name = "sangleDataStore")

    companion object {
        val deviceTokenKey = preferencesKey<String>("deviceToken")
        val tokenKey = preferencesKey<String>("token")
        val refreshTokenKey = preferencesKey<String>("refreshTokenKey")
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

}