package com.yjotdev.empprimaria

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
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
import com.yjotdev.empprimaria.presentation.utils.TestTags

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserInfoViewInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var navController: TestNavHostController // NavController del Test
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
        composeTestRule.onNodeWithTag(TestTags.LOGIN_USER_EMAIL_FIELD)
            .performTextInput("yasser")
        //Escribe clave de usuario
        composeTestRule.onNodeWithTag(TestTags.LOGIN_PASSWORD_FIELD)
            .performTextInput("Yjot1997")
        //Hace clic en el botón Iniciar Sesión
        composeTestRule.onNodeWithTag(TestTags.LOGIN_SUBMIT_BUTTON)
            .performClick()
        //Espera a que la corutina del botón Iniciar Sesión finalice
        composeTestRule.waitUntil(5000L) {
            navController.currentDestination?.route == ViewRoutes.UserInfo.name
        }
        //Verifica si la navegación a UserInfo fue exitosa
        assertEquals(ViewRoutes.UserInfo.name, navController.currentDestination?.route)
    }

    @Test
    fun navigationMenuToUserInfo() {
        navigateLoginToMenu()
        //Hace clic en el botón para ver la info del Usuario
        composeTestRule.onNodeWithTag(TestTags.TOP_BAR_USER_INFO)
            .performClick()
        //Verifica que esta en la vista de la info del Usuario
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_PHOTO_BUTTON)
            .assertExists()
    }

    @Test
    fun logout_UserInfoView() {
        navigationMenuToUserInfo()
        //Hace clic en el botón para cerrar sesión
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_LOGOUT_BUTTON)
            .performClick()
        //Verifica si la navegación a Login fue exitosa
        assertEquals(ViewRoutes.Login.name, navController.currentDestination?.route)
    }

    @Test
    fun updateUser_UserInfoView() {
        navigationMenuToUserInfo()
        //Escribe nueva clave de usuario
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_PASSWORD_FIELD)
            .performTextReplacement("Test1000")
        //Clic en el botón Enviar código
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_SEND_CODE_BUTTON)
            .performClick()
        //Escribe el código de verificación en el AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_INPUT_CODE)
            .performTextInput(code)
        //Clic en el botón Verificar del AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_CODE_CHECK)
            .performClick()
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_CODE_CHECK)
                    .assertDoesNotExist()
                true
            } catch (_: AssertionError){
                false
            }
        }
        //Clic en el botón Actualizar
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag(TestTags.USER_INFO_UPDATE_BUTTON)
                    .assertIsEnabled()
                true
            } catch (_: AssertionError){
                false
            }
        }
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_UPDATE_BUTTON)
            .performClick()
    }

    @Test
    fun deleteUser_UserInfoView() {
        navigationMenuToUserInfo()
        //Clic en el botón Enviar código
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_SEND_CODE_BUTTON)
            .performClick()
        //Escribe el código de verificación en el AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_INPUT_CODE)
            .performTextInput(code)
        //Clic en el botón Verificar del AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_CODE_CHECK)
            .performClick()
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_CODE_CHECK)
                    .assertDoesNotExist()
                true
            } catch (_: AssertionError){
                false
            }
        }
        //Clic en el botón Borrar cuenta
        composeTestRule.waitUntil(5000L) {
            try {
                composeTestRule.onNodeWithTag(TestTags.USER_INFO_DELETE_BUTTON)
                    .assertIsEnabled()
                true
            } catch (_: AssertionError){
                false
            }
        }
        composeTestRule.onNodeWithTag(TestTags.USER_INFO_DELETE_BUTTON)
            .performClick()
    }
}