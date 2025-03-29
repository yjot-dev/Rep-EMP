package com.yjotdev.empprimaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel
import com.yjotdev.empprimaria.application.navigation.PermissionView
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Inicializa el viewmodel
            val viewModel: ProgressViewModel = hiltViewModel()
            EmprendimientoPrimariaTheme {
                PermissionView(viewModel = viewModel)
            }
        }
    }
}