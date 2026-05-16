package com.yjotdev.empprimaria.presentation.navigation

import android.content.Context
import android.util.Log
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
import com.yjotdev.empprimaria.domain.utils.Stories
import com.yjotdev.empprimaria.domain.utils.Exercise1
import com.yjotdev.empprimaria.domain.utils.Exercise2
import com.yjotdev.empprimaria.domain.utils.Exercise3
import com.yjotdev.empprimaria.presentation.components.TitleBar
import com.yjotdev.empprimaria.presentation.components.LoadingScreen
import com.yjotdev.empprimaria.presentation.mvvm.ui.LevelView
import com.yjotdev.empprimaria.presentation.mvvm.ui.LoginView
import com.yjotdev.empprimaria.presentation.mvvm.ui.OpinionView
import com.yjotdev.empprimaria.presentation.mvvm.ui.ProjectListView
import com.yjotdev.empprimaria.presentation.mvvm.ui.RecoverKeyView
import com.yjotdev.empprimaria.presentation.mvvm.ui.RegisterView
import com.yjotdev.empprimaria.presentation.mvvm.ui.StoryView
import com.yjotdev.empprimaria.presentation.mvvm.ui.UnitsView
import com.yjotdev.empprimaria.presentation.mvvm.ui.UserInfoView
import com.yjotdev.empprimaria.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.empprimaria.presentation.mvvm.state.UiState
import com.yjotdev.empprimaria.R

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: UiViewModel,
    onCode: (String) -> Unit
){
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ViewRoutes.Login.name
    val currentScreen = ViewRoutes.valueOf(
        currentRoute.substringBefore("/")
    )
    val totalLevels = 5
    // Obtiene estados del viewmodel
    val state by viewModel.uiState.collectAsState()
    // Obtiene progreso de la unidad
    val progressUnit = (state.currentLevelNum * 100)/totalLevels //Progreso de la unidad
    // Observa temporizador
    ObserveTimerState(state = state) {
        viewModel.setTimeSpent(state.timeSpent + 1)
    }
    // Observa estados asincronicos
    ObserveViewModelState(
        viewModel = viewModel,
        navController = navController,
        context = context
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
    // UI
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
                onUserInfo = { navigateToMainScreen(navController, ViewRoutes.UserInfo.name) },
                onUnits = { navigateToMainScreen(navController, ViewRoutes.Units.name) },
                onProjects = { navigateToMainScreen(navController, ViewRoutes.Projects.name) },
                onOpinion = { navigateToMainScreen(navController, ViewRoutes.Opinion.name) },
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ViewRoutes.Login.name,
            enterTransition = { slideInHorizontally{ -300 } },
            exitTransition = { slideOutHorizontally{ 300 } },
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = ViewRoutes.Login.name) {
                LaunchedEffect(Unit) { viewModel.resetViewModel() }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    LoginView(
                        modifier = Modifier.fillMaxSize(),
                        onLogin = { nameOrEmail, password ->
                            viewModel.loginUser(nameOrEmail, password)
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
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email,
                                "Usuario", code)
                            viewModel.sendEmail(email, subject, text)
                        },
                        onRegister = { name, email, password ->
                            viewModel.insertUser(name, email, password)
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
                        },
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email,
                                "Usuario", code)
                            viewModel.sendEmail(email, subject, text)
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
            }
            composable(route = ViewRoutes.UserInfo.name) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    UserInfoView(
                        modifier = Modifier.fillMaxSize(),
                        userInfo = state.user,
                        isDialogDisplayed = state.isDialogDisplayed,
                        onIsDialogDisplayed = { displayed ->
                            viewModel.setIsDialogDisplayed(displayed)
                        },
                        onUserInfo = { id, param ->
                            when (id) {
                                1 -> viewModel.setUser(state.user.copy(name = param))
                                2 -> viewModel.setUser(state.user.copy(email = param))
                                3 -> viewModel.setUser(state.user.copy(password = param))
                            }
                        },
                        onLogout = { viewModel.logoutUser() },
                        onUpdate = { name, email, password, photo ->
                            viewModel.updateUser(state.user.id, name, email, password, photo)
                        },
                        onDelete = { viewModel.deleteUser(state.user.id) },
                        onSendCode = { email, code ->
                            onCode(code)
                            val subject = context.getString(R.string.alert_dialog_code)
                            val text = context.getString(R.string.body_email,
                                    state.user.name.uppercase(), code)
                            viewModel.sendEmail(email, subject, text)
                        }
                    )
                    if(state.isLoading) LoadingScreen()
                }
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
                                viewModel.setIsTimerOn(true)
                                navController.navigate("${ViewRoutes.Level.name}/$levelId")
                            }
                            "1.5" -> {
                                viewModel.setIsTimerOn(true)
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
                        val to = "emprendimiento2020g7h2@gmail.com"
                        val subject = context.getString(
                            R.string.alert_dialog_opinion,
                            state.user.name.uppercase()
                        )
                        viewModel.sendEmail(to, subject, text)
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
                    isBtnNextDisplayed = state.isBtnNextDisplayed,
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
                    onIsBtnNextDisplayed = { displayed ->
                        viewModel.setIsBtnNextDisplayed(displayed)
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
                            viewModel.setIsBtnNextDisplayed(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setIsBtnNextDisplayed(false)
                        viewModel.setIsTimerOn(false)
                        if (state.progressLevel == 1f) {
                            viewModel.setCourseCompleted(progressUnit)
                        }
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
                    isVisible = state.isBtnNextDisplayed,
                    progressLevel = state.progressLevel,
                    onProcess = { idSection, correct ->
                        val progressLevel = when(idSection){
                            1 -> 0.33f
                            2 -> 0.66f
                            else -> 1f
                        }
                        if(correct) {
                            viewModel.setExperience(state.experience + 20)
                            viewModel.setProgressLevel(progressLevel)
                            viewModel.setIsBtnNextDisplayed(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setIsBtnNextDisplayed(false)
                        viewModel.setIsTimerOn(false)
                        if (state.progressLevel == 1f) {
                            viewModel.setCourseCompleted(progressUnit)
                        }
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun ObserveTimerState(
    state: UiState,
    onProcess: () -> Unit
){
    LaunchedEffect(key1 = state.isTimerOn) {
        // Si el temporizador está apagado, no hacemos nada y salimos.
        if (!state.isTimerOn) return@LaunchedEffect
        // Aplicamos el temporizador
        while (true) {
            val minutes: Long = 1000 * 60
            delay(minutes) // Espera 1 minuto
            onProcess()
        }
    }
}

@Composable
private fun ObserveViewModelState(
    viewModel: UiViewModel,
    navController: NavHostController,
    context: Context
){
    LaunchedEffect(key1 = true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                /*
                Login -> UserInfo (Revisar ProgressViewModel.kt lineas 134 - 137)
                UserInfo -> Login (Revisar ProgressViewModel.kt lineas 117 - 124)
                */
                is UiEvent.Navigate -> navController.navigate(event.route){
                    popUpTo(event.routePopUp){ inclusive = true }
                }
                // Muestra un mensaje de exito o error en el Toast
                is UiEvent.ShowToast -> Toast.makeText(
                    context, event.message, Toast.LENGTH_SHORT
                ).show()
                // Muestra el error en el Log
                is UiEvent.ShowLog -> Log.d("Https",event.message)
            }
        }
    }
}

private fun navigateToMainScreen(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(route) {
            inclusive = false
        }
        launchSingleTop = true
    }
}