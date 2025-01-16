package com.jetbrains.kmpapp.platform

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jetbrains.kmpapp.constants.CONST.AUTH_TOKEN
import com.jetbrains.kmpapp.di.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class LocalStorage (private val dataStore: DataStore<Preferences>) : Storage {

    private val scope = CoroutineScope(Dispatchers.IO)

    override var authToken: String
        get() = getString(AUTH_TOKEN)
        set(value) = writeString(AUTH_TOKEN, value)

    override fun getTokenFromPrefsAsFlow(): Flow<String> =
        dataStore.data.map {
            it[stringPreferencesKey(AUTH_TOKEN)] ?: ""
        }


    private fun getBoolean(key: String): Boolean =
        runBlocking {
            withTimeoutOrNull(1000L) {
                dataStore.data.map { prefs ->
                    prefs[booleanPreferencesKey(key)]
                }.first()
            } == true
        }

    private fun writeBoolean(key: String, value: Boolean) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[booleanPreferencesKey(key)] = value
            }
        }
    }

    private fun getString(key: String): String =
        runBlocking {
            dataStore.data.map {
                it[stringPreferencesKey(key)]
            }.first() ?: ""
        }

    private fun writeString(key: String, value: String) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[stringPreferencesKey(key)] = value
            }
        }
    }

    private fun getLong(key: String): Long =
        runBlocking {
            withTimeoutOrNull(1000L) {
                dataStore.data.map { prefs ->
                    prefs[longPreferencesKey(key)]
                }.first()
            } ?: 0L
        }

    private fun writeLong(key: String, value: Long) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[longPreferencesKey(key)] = value
            }
        }
    }

    private fun getInt(key: String): Int =
        runBlocking {
            dataStore.data.map {
                it[intPreferencesKey(key)]
            }.first() ?: 0
        }

    private fun writeInt(key: String, value: Int) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[intPreferencesKey(key)] = value
            }
        }
    }
}