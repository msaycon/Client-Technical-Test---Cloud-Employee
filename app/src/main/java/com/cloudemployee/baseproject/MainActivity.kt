package com.cloudemployee.baseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cloudemployee.baseproject.presentation.navigation.BaseNavGraph
import com.cloudemployee.baseproject.presentation.theme.CloudEmployeeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CloudEmployeeTheme {
                BaseNavGraph()
            }
        }
    }
}

