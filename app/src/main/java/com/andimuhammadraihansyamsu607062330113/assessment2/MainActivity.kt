package com.andimuhammadraihansyamsu607062330113.assessment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.andimuhammadraihansyamsu607062330113.assessment2.navigation.SetupNavGraph
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.Assessment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assessment2Theme {
                SetupNavGraph()
            }
        }
    }
}