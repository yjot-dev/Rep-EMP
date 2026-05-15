package com.yjotdev.empprimaria

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
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
class UserInfoViewInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var navController: TestNavHostController // NavController del Test
    private val context: Context = ApplicationProvider.getApplicationContext() // Contexto del test de la app
    private lateinit var code: String

    @Before
    fun init() {
        hiltRule.inject() // Inicializa Hilt
    }

    @Test
    fun navigateLoginToMenu() {
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
        //Escribe nombre de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_user_email)
        ).performTextInput("yasser")
        //Escribe clave de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_password)
        ).performTextInput("Yjot1997")
        //Hace click en el boton Iniciar Sesión
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_login)
        ).performClick()
        //Espera a que la corutina del boton Iniciar Sesión finalice
        composeTestRule.waitUntil(5000L) {
            navController.currentDestination?.route == ViewRoutes.UserInfo.name
        }
        //Verifica si la navegacion a UserInfo fue exitosa
        assertEquals(ViewRoutes.UserInfo.name, navController.currentDestination?.route)
    }

    @Test
    fun navigationMenuToUserInfo() {
        navigateLoginToMenu()
        //Hace click en el boton para ver la info del Usuario
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.button_user_info)
        ).performClick()
        //Verifica que esta en la vista de la info del Usuario
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.image_user_info)
        ).assertExists()
    }

    @Test
    fun logout_UserInfoView() {
        navigationMenuToUserInfo()
        //Hace click en el boton para cerrar sesion
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_logout)
        ).performClick()
        //Verifica si la navegacion a Login fue exitosa
        assertEquals(ViewRoutes.Login.name, navController.currentDestination?.route)
    }

    @Test
    fun updateUser_UserInfoView() {
        navigationMenuToUserInfo()
        //Escribe nueva clave de usuario
        composeTestRule.onNodeWithText(
            context.getString(R.string.text_field_password)
        ).performTextReplacement("Test1000")
        //Click en el boton Enviar codigo
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_send_code)
        ).performClick()
        //Escribe el codigo de verificacion en el AlertDialog
        composeTestRule.onNodeWithTag("InputCode").performTextInput(code)
        //Click en el boton Verificar del AlertDialog
        composeTestRule.onNodeWithTag("CodeCheck").performClick()
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag("CodeCheck").assertDoesNotExist()
                true
            } catch (_: AssertionError){
                false
            }
        }
        //Click en el boton Actualizar
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithText(
                    context.getString(R.string.button_update)
                ).assertIsEnabled()
                true
            } catch (_: AssertionError){
                false
            }
        }
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_update)
        ).performClick()
    }

    @Test
    fun deleteUser_UserInfoView() {
        navigationMenuToUserInfo()
        //Click en el boton Enviar codigo
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_send_code)
        ).performClick()
        //Escribe el codigo de verificacion en el AlertDialog
        composeTestRule.onNodeWithTag("InputCode").performTextInput(code)
        //Click en el boton Verificar del AlertDialog
        composeTestRule.onNodeWithTag("CodeCheck").performClick()
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag("CodeCheck").assertDoesNotExist()
                true
            } catch (_: AssertionError){
                false
            }
        }
        //Click en el boton Borrar cuenta
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithText(
                    context.getString(R.string.button_delete)
                ).assertIsEnabled()
                true
            } catch (_: AssertionError){
                false
            }
        }
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_delete)
        ).performClick()
    }
}