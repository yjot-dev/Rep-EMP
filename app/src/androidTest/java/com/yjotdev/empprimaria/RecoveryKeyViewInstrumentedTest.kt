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
import com.yjotdev.empprimaria.presentation.navigation.PermissionView
import com.yjotdev.empprimaria.presentation.navigation.ViewRoutes
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RecoveryKeyViewInstrumentedTest {

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
    fun changePassword_RecoverKeyView() {
        var code = ""
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            EmprendimientoPrimariaTheme {
                PermissionView(
                    navController = navController,
                    onCode = {code = it}
                )
            }
        }
        //Hace click en el boton Recuperar clave
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_recover_key)
        ).performClick()
        //Verifica si la navegacion a Recuperar clave fue exitosa
        assertEquals(ViewRoutes.RecoverKey.name, navController.currentDestination?.route)
        //Escribe el email de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_email)
        ).performTextInput("2010guabo@gmail.com")
        //Click en el boton Enviar codigo
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_send_code)
        ).performClick()
        //Escribe el codigo de verificacion en el AlertDialog
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_code)
        ).performTextInput(code)
        //Click en el boton Verificar del AlertDialog
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_verify_code)
        ).performClick()
        //Escribe nueva contraseña
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_password_new)
        ).performTextInput("Yjot2025")
        //Click en el boton Cambiar contraseña
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_change_password)
        ).performClick()
    }
}