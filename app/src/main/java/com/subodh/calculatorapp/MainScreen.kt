package com.subodh.calculatorapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.subodh.calculatorapp.calculator.CalculatorScreen
import com.subodh.calculatorapp.theme.ThemeViewModel
import kotlinx.coroutines.delay
import com.subodh.calculatorapp.splash.SplashScreen


@Composable
fun MainScreen(themeViewModel: ThemeViewModel) {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 2 seconds
        showSplash = false
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (showSplash) {
            SplashScreen()
        } else {
            CalculatorScreen(themeViewModel = themeViewModel)
        }
    }
}

