package com.yjotdev.empprimaria.application.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.yjotdev.empprimaria.application.components.LoadingScreen
import com.yjotdev.empprimaria.application.mvvm.view.LoginView
import com.yjotdev.empprimaria.application.mvvm.view.MenuView
import com.yjotdev.empprimaria.application.mvvm.view.RecoverKeyView
import com.yjotdev.empprimaria.application.mvvm.view.RegisterView
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel
import kotlinx.coroutines.delay

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
    val state by viewModel.uiState.collectAsState()
    //Variables reactivas locales
    var passwordR by remember { mutableStateOf("") }
    var operationId by remember { mutableIntStateOf(0) }
    var progressLevel by remember { mutableFloatStateOf(0f) }
    var isVisible by remember { mutableStateOf(false) }
    var isTimerOff by remember { mutableStateOf(false) }
    val numLevel = 1
    val totalLevels = 5
    val progressUnit = (numLevel * 100)/totalLevels //Progreso de la unidad
    //UI
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
        ObserveViewModelState(
            viewModel = viewModel,
            navController = navController,
            context = context,
            operationId = operationId,
            passwordR = passwordR,
            progressLevel = progressLevel,
            progressUnit = progressUnit,
            onIsVisible = { isVisible = it },
            isTimerOff = isTimerOff,
            onIsTimerOff = { isTimerOff = it }
        )
        NavHost(
            navController = navController,
            startDestination = ViewRoutes.Login.name,
            enterTransition = { slideInHorizontally{ -300 } },
            exitTransition = { slideOutHorizontally{ 300 } },
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = ViewRoutes.Login.name) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    LoginView(
                        modifier = Modifier.fillMaxSize(),
                        onLogin = { userOrEmail, password ->
                            viewModel.findUser(userOrEmail, userOrEmail, password)
                            operationId = 1
                            passwordR = password
                        },
                        onRegister = { navController.navigate(ViewRoutes.Register.name) },
                        onRecoverKey = { navController.navigate(ViewRoutes.RecoverKey.name) }
                    )
                    if(state.isLoading) LoadingScreen()
                }
            }
            composable(route = ViewRoutes.Register.name) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    RegisterView(
                        modifier = Modifier.fillMaxSize(),
                        onRegister = { user, email, password ->
                            viewModel.insertUser(user, email, password)
                            operationId = 2
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
            }
            composable(route = ViewRoutes.RecoverKey.name) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    RecoverKeyView(
                        modifier = Modifier.fillMaxSize(),
                        onChangePassword = { email, password ->
                            viewModel.changePassword(email, password)
                            operationId = 3
                        },
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email, "Usuario", code)
                            viewModel.sendCodeByEmail(email, subject, text)
                            operationId = 4
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
            }
            composable(route = ViewRoutes.Menu.name) {
                val id = userInfo.id
                val nombre = userInfo.name
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    MenuView(
                        modifier = Modifier.fillMaxSize(),
                        myName = userInfo.name,
                        myEmail = userInfo.email,
                        myPassword = userInfo.password,
                        myPhoto = userInfo.photo,
                        myExperience = state.experience,
                        myTimeSpent = state.timeSpent,
                        myCourseCompleted = state.courseCompleted,
                        myLife = state.life,
                        onLogout = { navController.navigate(ViewRoutes.Login.name){
                            popUpTo(ViewRoutes.Menu.name){ inclusive = true }}
                            viewModel.resetViewModel()
                        },
                        onUpdate = { user, email, password, photo ->
                            viewModel.updateUser(id, user, email, password, photo)
                            operationId = 5
                        },
                        onDelete = {
                            viewModel.deleteUser(id)
                            operationId = 6
                        },
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email, nombre, code)
                            viewModel.sendCodeByEmail(email, subject, text)
                            operationId = 7
                        },
                        onSendOpinion = { text ->
                            val subject = context.getString(R.string.alert_dialog_opinion, nombre.uppercase())
                            viewModel.sendCommentaryByEmail(subject, text)
                            operationId = 8
                        },
                        progressLevel = progressLevel,
                        onProgressLevel = { progressLevel = it },
                        isVisible = isVisible,
                        onIsVisible = { isVisible = it },
                        onIsTimerOff = { isTimerOff = it },
                        onCallback = { id, isCorrect ->
                            when(id){
                                -3 -> {
                                    operationId = 9
                                    viewModel.setOperationCompletedCount()
                                }
                                -2 -> {
                                    operationId = 10
                                    viewModel.setOperationCompletedCount()
                                }
                                -1 -> {
                                    viewModel.setCourseCompleted(progressUnit)
                                }
                                1 -> {
                                    isCorrect?.let {
                                        if(it) {
                                            progressLevel = 0.33f
                                            viewModel.setExperience(state.experience + 20)
                                            isVisible = true
                                        }else{
                                            viewModel.setLife(state.life - 1)
                                        }
                                    }
                                }
                                2 -> {
                                    isCorrect?.let {
                                        if(it) {
                                            progressLevel = 0.66f
                                            viewModel.setExperience(state.experience + 20)
                                            isVisible = true
                                        }else{
                                            viewModel.setLife(state.life - 1)
                                        }
                                    }
                                }
                                3 -> {
                                    isCorrect?.let {
                                        if(it) {
                                            progressLevel = 1f
                                            viewModel.setExperience(state.experience + 20)
                                            isVisible = true
                                        }else{
                                            viewModel.setLife(state.life - 1)
                                        }
                                    }
                                }
                                101 -> {
                                    isCorrect?.let {
                                        if (it) {
                                            viewModel.setExperience(state.experience + 20)
                                        } else {
                                            viewModel.setLife(state.life - 1)
                                        }
                                    }
                                }
                            }
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
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

@Composable
private fun ObserveViewModelState(
    viewModel: ProgressViewModel,
    navController: NavHostController,
    context: Context,
    operationId: Int,
    passwordR: String,
    progressLevel: Float,
    progressUnit: Int,
    onIsVisible: (Boolean) -> Unit,
    isTimerOff: Boolean,
    onIsTimerOff: (Boolean) -> Unit
){
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(
        key1 = state.operationCompletedCount
    ) {
        // No ejecutar si operationCompletedCount es 0 (estado inicial)
        if (state.operationCompletedCount == 0) return@LaunchedEffect
        when(operationId){
            1 -> {
                state.user?.let { user ->
                    if(state.wasFound) viewModel.clearFlags()
                    viewModel.setUserInfo(user.copy(password = passwordR))
                    navController.navigate(ViewRoutes.Menu.name){
                        popUpTo(ViewRoutes.Login.name){ inclusive = true }
                    }
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_user_login)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                if (state.wasInserted) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_user_registered)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            3 -> {
                if (state.wasUpdated) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_changed_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_user_changed_password)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            4 -> {
                if (state.wasEmailed) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_email_sent)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            5 -> {
                if (state.wasUpdated) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_user_updated)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            6 -> {
                if (state.wasDeleted) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_user_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_user_deleted)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            7 -> {
                if (state.wasEmailed) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_email_sent)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            8 -> {
                if (state.wasEmailed) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                state.error?.let { error ->
                    val msm = "${context.getString(R.string.error_email_sent)}, $error"
                    Toast.makeText(context, msm, Toast.LENGTH_SHORT).show()
                }
            }
            9 -> {
                do{
                    //Temporizador de StoryView
                    delay(1000 * 60)
                    viewModel.setTimeSpent(state.timeSpent + 1)
                    if (progressLevel == 1f) {
                        viewModel.setCourseCompleted(progressUnit)
                        onIsTimerOff(true)
                        onIsVisible(true)
                    }
                }while(!isTimerOff)
            }
            10 -> {
                do{
                    //Temporizador de LevelView
                    delay(1000 * 60)
                    viewModel.setTimeSpent(state.timeSpent + 1)
                }while(!isTimerOff)
            }
        }
        viewModel.clearFlags()
    }
}