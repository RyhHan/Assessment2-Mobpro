package com.andimuhammadraihansyamsu607062330113.assessment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.andimuhammadraihansyamsu607062330113.assessment2.navigation.SetupNavGraph
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.ThemeController
import com.andimuhammadraihansyamsu607062330113.assessment2.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStore = SettingsDataStore(LocalContext.current)
            val isGreenTheme = dataStore.themeFlow.collectAsState(initial = false).value

            ThemeController(isGreenTheme = isGreenTheme) {
                SetupNavGraph()
            }
        }
    }
}