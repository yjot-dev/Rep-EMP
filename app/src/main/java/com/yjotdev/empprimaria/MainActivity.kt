package com.yjotdev.empprimaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.empprimaria.application.navigation.PermissionView
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!isRunningTest()) {
            setContent {
                EmprendimientoPrimariaTheme {
                    PermissionView()
                }
            }
        }
    }

    private fun isRunningTest(): Boolean {
        return BuildConfig.DEBUG && Thread.currentThread().stackTrace.any {
            it.className.contains("androidx.test")
        }
    }
}