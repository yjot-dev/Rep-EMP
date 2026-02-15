package com.yjotdev.empprimaria.application.navigation

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
    val state by viewModel.uiState.collectAsState()
    //Observa temporizador
    ObserveTimerState(viewModel = viewModel)
    //Observa estados asincronicos
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
                            val text = context.getString(R.string.body_email, "Usuario", code)
                            viewModel.sendCodeByEmail(email, subject, text)
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
                            val text =
                                context.getString(R.string.body_email, state.user.name, code)
                            viewModel.sendCodeByEmail(email, subject, text)
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
                            state.user.name.uppercase()
                        )
                        viewModel.sendCommentaryByEmail(subject, text)
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
                            viewModel.setIsBtnNextDisplayed(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setIsBtnNextDisplayed(false)
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
                    isVisible = state.isBtnNextDisplayed,
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
                            viewModel.setIsBtnNextDisplayed(true)
                        }else{
                            viewModel.setLife(state.life - 1)
                        }
                    },
                    onCallback = {
                        viewModel.setProgressLevel(0f)
                        viewModel.setIsBtnNextDisplayed(false)
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
                viewModel.setIsBtnNextDisplayed(true)
            }
        }while(!state.isTimerOff)
    }
}

@Composable
private fun ObserveViewModelState(
    viewModel: ProgressViewModel,
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
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}