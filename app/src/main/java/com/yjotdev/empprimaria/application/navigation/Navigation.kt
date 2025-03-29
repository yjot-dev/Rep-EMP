package com.yjotdev.empprimaria.application.navigation

import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.application.mvvm.view.LoginView
import com.yjotdev.empprimaria.application.mvvm.view.MenuView
import com.yjotdev.empprimaria.application.mvvm.view.RecoverKeyView
import com.yjotdev.empprimaria.application.mvvm.view.RegisterView
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel

@Composable
fun NavigationView(
    navController: NavHostController,
    viewModel: ProgressViewModel,
    onCode: (String) -> Unit
){
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ViewRoutes.valueOf(
        backStackEntry?.destination?.route ?: ViewRoutes.Login.name
    )
    //Obtiene datos del viewmodel
    val userInfo by viewModel.userInfo.collectAsState()
    Scaffold(
        topBar = {
            TitleBar(
                viewRoutes = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null
                        && currentScreen != ViewRoutes.Menu,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ViewRoutes.Login.name,
            enterTransition = { slideInHorizontally{ -300 } },
            exitTransition = { slideOutHorizontally{ 300 } },
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = ViewRoutes.Login.name) {
                LoginView(
                    modifier = Modifier.fillMaxSize(),
                    progressVm = viewModel,
                    onLogin = { userOrEmail, password ->
                        viewModel.findUser(userOrEmail, password){ user ->
                            if(user.id != 0){
                                viewModel.setUserInfo(user.copy(clave = password))
                                navController.navigate(ViewRoutes.Menu.name){
                                    popUpTo(ViewRoutes.Login.name){ inclusive = true }
                                }
                            }else{
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_user_login),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onRegister = { navController.navigate(ViewRoutes.Register.name) },
                    onRecoverKey = { navController.navigate(ViewRoutes.RecoverKey.name) }
                )
            }
            composable(route = ViewRoutes.Register.name) {
                RegisterView(
                    modifier = Modifier.fillMaxSize(),
                    progressVm = viewModel,
                    onRegister = { user, email, password ->
                        viewModel.insertUser(user, email, password){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_user_registered),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_user_registered),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
            composable(route = ViewRoutes.RecoverKey.name) {
                RecoverKeyView(
                    modifier = Modifier.fillMaxSize(),
                    progressVm = viewModel,
                    onChangePassword = { email, password ->
                        viewModel.changePassword(email, password){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_user_changed_password),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_user_changed_password),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onSendCode = { email, code ->
                        onCode(code)
                        val subject = context.getString(R.string.alert_dialog_code)
                        val text = context.getString(R.string.body_email, "Usuario", code)
                        viewModel.sendCodeByEmail(email, subject, text){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
            composable(route = ViewRoutes.Menu.name) {
                val id = userInfo.id
                val nombre = userInfo.nombre
                MenuView(
                    modifier = Modifier.fillMaxSize(),
                    progressVm = viewModel,
                    onLogout = { navController.navigate(ViewRoutes.Login.name){
                        popUpTo(ViewRoutes.Menu.name){ inclusive = true }}
                        viewModel.reset()
                    },
                    onUpdate = { user, email, password, photo ->
                        viewModel.updateUser(id, user, email, password, photo){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_user_updated),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_user_updated),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onDelete = {
                        viewModel.deleteUser(id){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_user_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_user_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onSendCode = { email, code ->
                        onCode(code)
                        val subject = context.getString(R.string.alert_dialog_code)
                        val text = context.getString(R.string.body_email, nombre, code)
                        viewModel.sendCodeByEmail(email, subject, text){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onSendOpinion = { text ->
                        val subject = context.getString(R.string.alert_dialog_opinion, nombre.uppercase())
                        viewModel.sendCommentaryByEmail(subject, text){ result ->
                            if (result) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.alert_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_email_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TitleBar(
    viewRoutes: ViewRoutes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
){
    if(canNavigateBack) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = viewRoutes.idTitle),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dm_5))
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dm_5))
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}