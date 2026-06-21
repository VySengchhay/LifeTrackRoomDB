package com.androidapp.lifetrackroomdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.androidapp.lifetrackroomdb.presentation.Test
import com.example.lifetrackpro.ui.theme.LifeTrackProTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var isCheck = true
        lifecycleScope.launch {
            if (isCheck) {
                delay(3000)
                isCheck = false
            }
        }
        splashScreen.apply {
            setKeepOnScreenCondition {
                isCheck
            }
        }
        enableEdgeToEdge()
        setContent {
            LifeTrackProTheme(
                dynamicColor = false
            ) {
                Test()
            }
        }
    }
}
