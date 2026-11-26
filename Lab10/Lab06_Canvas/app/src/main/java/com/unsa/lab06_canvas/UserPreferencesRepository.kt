package com.unsa.lab06_canvas

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferencesRepository(private val context: Context) {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val LAST_SYNC_KEY = longPreferencesKey("last_sync_timestamp")
    private val SYNC_COUNT_KEY = intPreferencesKey("sync_count")

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    // Lab 10: marca de tiempo (ms) de la última sincronización en segundo plano; 0 = nunca
    val ultimaSincronizacionFlow: Flow<Long> = context.dataStore.data
        .map { preferences -> preferences[LAST_SYNC_KEY] ?: 0L }

    // Lab 10: cantidad acumulada de sincronizaciones ejecutadas por WorkManager
    val totalSincronizacionesFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[SYNC_COUNT_KEY] ?: 0 }

    /** Guarda la marca de tiempo y aumenta el contador. Devuelve el nuevo total. */
    suspend fun registrarSincronizacion(timestamp: Long): Int {
        var total = 0
        context.dataStore.edit { preferences ->
            total = (preferences[SYNC_COUNT_KEY] ?: 0) + 1
            preferences[SYNC_COUNT_KEY] = total
            preferences[LAST_SYNC_KEY] = timestamp
        }
        return total
    }
}
