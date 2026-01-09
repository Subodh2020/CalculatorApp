package com.subodh.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.subodh.calculatorapp.theme.ThemeViewModel
import com.subodh.calculatorapp.theme.ThemeViewModelFactory
import com.subodh.calculatorapp.ui.theme.CalculatorAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModelFactory(this)
            )
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            
            // Use user preference if available, otherwise use system theme
            val darkTheme = isDarkTheme ?: isSystemInDarkTheme()

            CalculatorAppTheme (darkTheme = darkTheme) {
                MainScreen(themeViewModel = themeViewModel)
            }
        }
    }
}