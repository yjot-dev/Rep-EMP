package com.yjotdev.empprimaria.application.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import com.yjotdev.empprimaria.R
import com.yjotdev.empprimaria.domain.utils.data.Stories
import com.yjotdev.empprimaria.domain.utils.data.Exercise1
import com.yjotdev.empprimaria.domain.utils.data.Exercise2
import com.yjotdev.empprimaria.domain.utils.data.Exercise3
import com.yjotdev.empprimaria.application.components.TitleBar
import com.yjotdev.empprimaria.application.components.LoadingScreen
import com.yjotdev.empprimaria.application.mvvm.view.LevelView
import com.yjotdev.empprimaria.application.mvvm.view.LoginView
import com.yjotdev.empprimaria.application.mvvm.view.OpinionView
import com.yjotdev.empprimaria.application.mvvm.view.ProjectListView
import com.yjotdev.empprimaria.application.mvvm.view.RecoverKeyView
import com.yjotdev.empprimaria.application.mvvm.view.RegisterView
import com.yjotdev.empprimaria.application.mvvm.view.StoryView
import com.yjotdev.empprimaria.application.mvvm.view.UnitsView
import com.yjotdev.empprimaria.application.mvvm.view.UserInfoView
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: ProgressViewModel,
    onCode: (String) -> Unit
){
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ViewRoutes.Login.name
    val currentScreen = ViewRoutes.valueOf(
        currentRoute.substringBefore("/")
    )
    //Obtiene estados del viewmodel
    val userInfo by viewModel.userInfo.collectAsState()
    val state by viewModel.uiState.collectAsState()
    //Variables reactivas locales
    var passwordR by remember { mutableStateOf("") }
    //Observa temporizador
    ObserveTimerState(viewModel = viewModel)
    //Observa estados asincronicos
    ObserveViewModelState(
        viewModel = viewModel,
        navController = navController,
        context = context,
        passwordR = passwordR
    )
    // Define las rutas que no mostrarán el boton regresar
    val screensWithoutNavigateBack = setOf(
        ViewRoutes.Login,
        ViewRoutes.UserInfo,
        ViewRoutes.Units,
        ViewRoutes.Projects,
        ViewRoutes.Opinion
    )
    // Validar el regresar manual
    if(currentScreen in screensWithoutNavigateBack){
        BackHandler(enabled = true){}
    }
    //UI
    Scaffold(
        topBar = {
            TitleBar(
                modifier = Modifier.fillMaxWidth(),
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null
                        && currentScreen !in screensWithoutNavigateBack,
                progressLevel = state.progressLevel,
                myLife = state.life,
                navigateUp = { navController.navigateUp() },
                onUserInfo = { navController.navigate(ViewRoutes.UserInfo.name){
                    popUpTo(ViewRoutes.UserInfo.name)
                    launchSingleTop = true
                } },
                onUnits = { navController.navigate(ViewRoutes.Units.name){
                    popUpTo(ViewRoutes.Units.name)
                    launchSingleTop = true
                } },
                onProjects = { navController.navigate(ViewRoutes.Projects.name){
                    popUpTo(ViewRoutes.Projects.name)
                    launchSingleTop = true
                } },
                onOpinion = { navController.navigate(ViewRoutes.Opinion.name){
                    popUpTo(ViewRoutes.Opinion.name)
                    launchSingleTop = true
                } }
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    LoginView(
                        modifier = Modifier.fillMaxSize(),
                        onLogin = { userOrEmail, password ->
                            viewModel.findUser(userOrEmail, userOrEmail, password)
                            viewModel.setCurrentOperationId(1)
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
                            viewModel.setCurrentOperationId(2)
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
                            viewModel.setCurrentOperationId(3)
                        },
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email, "Usuario", code)
                            viewModel.sendCodeByEmail(email, subject, text)
                            viewModel.setCurrentOperationId(4)
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
            }
            composable(route = ViewRoutes.UserInfo.name) {
                UserInfoView(
                    modifier = Modifier.fillMaxSize(),
                    userInfo = userInfo,
                    onUserInfo = { id, param ->
                        when(id){
                            1 -> { viewModel.setUserInfo(userInfo.copy(name = param)) }
                            2 -> { viewModel.setUserInfo(userInfo.copy(email = param)) }
                            3 -> { viewModel.setUserInfo(userInfo.copy(password = param)) }
                        }
                    },
                    onLogout = {
                        navController.navigate(ViewRoutes.Login.name){
                            popUpTo(ViewRoutes.UserInfo.name){ inclusive = true }
                        }
                        viewModel.resetViewModel()
                    },
                    onUpdate = { user, email, password, photo ->
                        viewModel.updateUser(userInfo.id, user, email, password, photo)
                        viewModel.setCurrentOperationId(5)
                    },
                    onDelete = {
                        viewModel.deleteUser(userInfo.id)
                        viewModel.setCurrentOperationId(6)
                    },
                    onSendCode = { email, code ->
                        onCode(code)
                        val subject = context.getString(R.string.alert_dialog_code)
                        val text = context.getString(R.string.body_email, userInfo.name, code)
                        viewModel.sendCodeByEmail(email, subject, text)
                        viewModel.setCurrentOperationId(7)
                    }
                )
            }
            composable(route = ViewRoutes.Units.name) {
                UnitsView(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(ScrollState(0)),
                    myCourseCompleted = state.courseCompleted,
                    onNavigationToLevel = { levelId ->
                        when(levelId){
                            "1.1", "1.2", "1.3", "1.4" -> {
                                viewModel.setTimeSpent(1)
                                navController.navigate("${ViewRoutes.Level.name}/$levelId")
                            }
                            "1.5" -> {
                                viewModel.setTimeSpent(1)
                                navController.navigate("${ViewRoutes.Story.name}/$levelId")
                            }
                        }
                    }
                )
            }
            composable(route = ViewRoutes.Projects.name) {
                ProjectListView(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(ScrollState(0))
                )
            }
            composable(route = ViewRoutes.Opinion.name) {
                OpinionView(
                    modifier = Modifier.fillMaxSize(),
                    myExperience = state.experience,
                    myTimeSpent = state.timeSpent,
                    myCourseCompleted = state.courseCompleted,
                    onSendOpinion = { text ->
                        val subject = context.getString(
                            R.string.alert_dialog_opinion,
                            userInfo.name.uppercase()
                        )
                        viewModel.sendCommentaryByEmail(subject, text)
                        viewModel.setCurrentOperationId(8)
                    }
                )
            }
            composable(
                route = "${ViewRoutes.Level.name}/{levelId}",
                arguments = listOf(navArgument("levelId"){ type = NavType.StringType })
            ) { backStackEntry ->
                val levelId = backStackEntry.arguments?.getString("levelId") ?: "1.1"
                val levelNum = levelId.last().digitToInt() // Convierte "1.1" a 1
                viewModel.setCurrentLevelNum(levelNum)
                LevelView(
                    modifier = Modifier.fillMaxSize(),
                    myExperience = state.experience,
                    myTimeSpent = state.timeSpent,
                    myCourseCompleted = state.courseCompleted,
                    myLife = state.life,
                    isVisible = state.isDialogVisible,
                    exercise1 = when(levelNum){
                            1 -> Exercise1.data[0]
                            2 -> Exercise1.data[1]
                            3 -> Exercise1.data[2]
                            4 -> Exercise1.data[3]
                            else -> Exercise1.data[0]
                        },
                    exercise2 = when(levelNum){
                        1 -> Exercise2.data[0]
                        2 -> Exercise2.data[1]
                        3 -> Exercise2.data[2]
                        4 -> Exercise2.data[3]
                        else -> Exercise2.data[0]
                    },
                    exercise3 = when(levelNum){
                        1 -> Exercise3.data[0]
                        2 -> Exercise3.data[1]
                        3 -> Exercise3.data[2]
                        4 -> Exercise3.data[3]
                        else -> Exercise3.data[0]
                    },
                    onIsVisible = { isVisible ->
                        viewModel.setDialogVisible(isVisible)
                    },
                    onIsTimerOff = { isTimerOff ->
                        viewModel.setIsTimerOff(isTimerOff)
                    },
                    onProcess = { idExercise, correct ->
                        val progressLevel = when(idExercise){
                            1 -> 0.33f
                            2 -> 0.66f
                            else -> 1f
                        }
                        if(correct) {
                            viewModel.setExperience(state.experience + 20)
                            viewModel.setProgressLevel(progressLevel)
                            viewModel.setDialogVisible(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setDialogVisible(false)
                        viewModel.setIsTimerOff(false)
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = "${ViewRoutes.Story.name}/{levelId}",
                arguments = listOf(navArgument("levelId"){ type = NavType.StringType })
            ) { backStackEntry ->
                val levelId = backStackEntry.arguments?.getString("levelId") ?: "1.5"
                val levelNum = levelId.last().digitToInt() // Convierte "1.5" a 5
                viewModel.setCurrentLevelNum(levelNum)
                StoryView(
                    modifier = Modifier.fillMaxSize(),
                    story = Stories.data[0],
                    myLife = state.life,
                    isVisible = state.isDialogVisible,
                    progressLevel = state.progressLevel,
                    onIsTimerOff = { isTimerOff ->
                        viewModel.setIsTimerOff(isTimerOff)
                    },
                    onProcess = { idSection, correct ->
                        val progressLevel = when(idSection){
                            1 -> 0.33f
                            2 -> 0.66f
                            else -> 1f
                        }
                        if(correct) {
                            viewModel.setExperience(state.experience + 20)
                            viewModel.setProgressLevel(progressLevel)
                            viewModel.setDialogVisible(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setDialogVisible(false)
                        viewModel.setIsTimerOff(false)
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun ObserveTimerState(
    viewModel: ProgressViewModel
){
    val state by viewModel.uiState.collectAsState()
    val totalLevels = 5
    val progressUnit = (state.currentLevelNum * 100)/totalLevels //Progreso de la unidad
    LaunchedEffect(
        key1 = state.timeSpent
    ) {
        // No ejecutar si timeSpent es 0 (estado inicial)
        if (state.timeSpent == 0) return@LaunchedEffect
        // Aplicamos el temporizador
        do{
            //Demora un segundo para actualizar timeSpent
            delay(1000)
            viewModel.setTimeSpent(state.timeSpent + 1)
            if (state.progressLevel == 1f) {
                viewModel.setCourseCompleted(progressUnit)
                viewModel.setIsTimerOff(true)
                viewModel.setDialogVisible(true)
            }
        }while(!state.isTimerOff)
    }
}

@Composable
private fun ObserveViewModelState(
    viewModel: ProgressViewModel,
    navController: NavHostController,
    context: Context,
    passwordR: String
){
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(
        key1 = state.operationCompletedCount
    ) {
        // No ejecutar si operationCompletedCount es 0 (estado inicial)
        if (state.operationCompletedCount == 0) return@LaunchedEffect
        // Usamos el operationId del state
        when(state.currentOperationId){
            1 -> {
                state.user?.let { user ->
                    if(state.wasFound) viewModel.clearFlags()
                    viewModel.setUserInfo(user.copy(password = passwordR))
                    navController.navigate(ViewRoutes.UserInfo.name){
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
        }
        viewModel.clearFlags()
    }
}