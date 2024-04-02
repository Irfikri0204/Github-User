package com.irfikri.githubuser.ui.tema

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("setting")

class TemaDatastore constructor(context: Context) {
    val THEME_KEY = booleanPreferencesKey("theme")
    private val settingDataStores = context.dataStore
    fun getTheme(): Flow<Boolean> =
        settingDataStores.data.map { preference ->
            preference[THEME_KEY]?:false
        }
    suspend fun saveTheme(isSystemDarkEnabled:Boolean){
        settingDataStores.edit { preferences ->
            preferences[THEME_KEY] = isSystemDarkEnabled
        }
    }
}