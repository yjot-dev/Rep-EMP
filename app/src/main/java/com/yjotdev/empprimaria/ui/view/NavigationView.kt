package com.yjotdev.empprimaria.ui.view

import androidx.annotation.StringRes
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.ui.model.UserModel
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.ui.viewmodel.ProgressViewModel
import com.yjotdev.empprimaria.ui.viewmodel.UserController

private enum class ViewRoutes(@StringRes val idTitle: Int){
    Login(idTitle = 0),
    Register(idTitle = R.string.button_register),
    RecoverKey(idTitle = R.string.button_recover_key),
    Menu(idTitle = 0)
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

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
){
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ViewRoutes.valueOf(
        backStackEntry?.destination?.route ?: ViewRoutes.Login.name
    )
    val userController = UserController()
    val progressVM = ProgressViewModel()
    var userInfo by remember { mutableStateOf(UserModel()) }
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
                    onLogin = { userOrEmail, password ->
                        userController.login(context, userOrEmail, password){ user ->
                            userInfo = user.copy(clave = password)
                            navController.navigate(ViewRoutes.Menu.name){
                                popUpTo(ViewRoutes.Login.name){ inclusive = true }
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
                    onRegister = { user, email, password ->
                        userController.register(context, user, email, password)
                    }
                )
            }
            composable(route = ViewRoutes.RecoverKey.name) {
                val id = userInfo.id
                val nombre = userInfo.nombre
                val correo = userInfo.correo
                val foto = userInfo.foto
                RecoverKeyView(
                    modifier = Modifier.fillMaxSize(),
                    onChangePassword = { password ->
                        userController.update(context, id, nombre, correo, password, foto)
                    },
                    onSendCode = { email, code ->
                        userController.sendCodeByEmail(context, email, nombre.uppercase(), code)
                    }
                )
            }
            composable(route = ViewRoutes.Menu.name) {
                val id = userInfo.id
                val nombre = userInfo.nombre
                MenuView(
                    modifier = Modifier.fillMaxSize(),
                    progressVm = progressVM,
                    userInfo = userInfo,
                    onLogout = { navController.navigate(ViewRoutes.Login.name){
                        popUpTo(ViewRoutes.Menu.name){ inclusive = true }}
                        progressVM.reset()
                    },
                    onUpdate = { user, email, password, photo ->
                        userController.update(context, id, user, email, password, photo)
                    },
                    onDelete = { userController.delete(context, id) },
                    onSendCode = { email, code ->
                        userController.sendCodeByEmail(context, email, nombre.uppercase(), code)
                    },
                    onSendOpinion = { text ->
                        userController.sendCommentaryByEmail(context, text, nombre.uppercase())
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewNavigation(){
    EmprendimientoPrimariaTheme{
        Navigation()
    }
}