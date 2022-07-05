package com.example.issue_tracker.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun saveJwt(key: Preferences.Key<String>, value: String) {
        context.settingDataStore.edit { mutablePreferences ->
            mutablePreferences[key] = value
        }
    }

    fun getJwt(key: Preferences.Key<String>) =
        context.settingDataStore.data.map { preferences ->
            preferences[key] ?: EMPTY_JWT
        }

    companion object {
        val JWT_KEY = stringPreferencesKey("jwt")
        private const val EMPTY_JWT = ""
    }
}