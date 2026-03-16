package com.meditation.tally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meditation.tally.ui.navigation.AppNavGraph
import com.meditation.tally.ui.theme.MeditationTallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as MeditationTallyApplication).appContainer
        setContent {
            val preferences by appContainer.preferencesRepository.observePreferences()
                .collectAsStateWithLifecycle(initialValue = appContainer.preferencesRepository.defaultPreferences())

            MeditationTallyTheme(
                themeMode = preferences.themeMode,
                themeColor = preferences.themeColor
            ) {
                AppNavGraph(appContainer = appContainer)
            }
        }
    }
}

