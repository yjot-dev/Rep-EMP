package com.yjotdev.empprimaria

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yjotdev.empprimaria.ui.theme.EmprendimientoPrimariaTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterViewInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // Contexto del test de la app.
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun createUser_RegisterView() {
        // NavController del Test
        val navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        composeTestRule.setContent {
            EmprendimientoPrimariaTheme {
                PermissionView(navController)
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