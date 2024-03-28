package com.root14.hoopoe.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "HoopoeFavorites")

class DataStoreUtil(private var context: Context) {
    fun readData(key: String) = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)]
    }

    suspend fun writeData(key: String, value: String, clearDataStore: Boolean = false) =
        context.dataStore.edit { settings ->
            if (clearDataStore) {
                settings.clear()
            }
            settings[stringPreferencesKey(key)] = value
        }


}