package com.subodh.calculatorapp.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")
private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

class ThemeViewModel(context: Context) : ViewModel() {
    private val dataStore = context.dataStore
    
    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    init {
        loadThemePreference()
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            val preferences = dataStore.data.first()
            val darkTheme = preferences[DARK_THEME_KEY] ?: false
            _isDarkTheme.value = darkTheme
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val currentTheme = _isDarkTheme.value == true
            val newTheme = !currentTheme
            _isDarkTheme.value = newTheme
            saveThemePreference(newTheme)
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            _isDarkTheme.value = isDark
            saveThemePreference(isDark)
        }
    }

    private suspend fun saveThemePreference(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }
}

class ThemeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

