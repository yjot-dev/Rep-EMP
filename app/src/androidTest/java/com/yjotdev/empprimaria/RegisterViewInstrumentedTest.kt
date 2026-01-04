package com.yjotdev.empprimaria

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import dagger.hilt.android.testing.HiltAndroidTest
import com.yjotdev.empprimaria.application.navigation.PermissionView
import com.yjotdev.empprimaria.application.navigation.ViewRoutes
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RegisterViewInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var navController: TestNavHostController // NavController del Test
    private val context: Context = ApplicationProvider.getApplicationContext() // Contexto del test de la app

    @Before
    fun init() {
        hiltRule.inject() // Inicializa Hilt
    }

    @Test
    fun createUser_RegisterView() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            EmprendimientoPrimariaTheme {
                PermissionView(navController = navController)
            }
        }
        //Hace click en el boton Registrarse
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_register)
        ).performClick()
        //Verifica si la navegacion a Registrarse fue exitosa
        assertEquals(ViewRoutes.Register.name, navController.currentDestination?.route)
        //Escribe nombre de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_user)
        ).performTextInput("juan")
        //Escribe email de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_email)
        ).performTextInput("juan@gmail.com")
        //Escribe clave de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_password)
        ).performTextInput("Juan0001")
        //Hace click en el boton Registrarse
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_create_user)
        ).performClick()
    }
}