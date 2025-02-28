package com.yjotdev.empprimaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmprendimientoPrimariaTheme {
                PermissionView()
            }
        }
    }
}