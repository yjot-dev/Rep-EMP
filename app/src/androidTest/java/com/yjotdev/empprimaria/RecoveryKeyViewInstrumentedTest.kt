package com.yjotdev.empprimaria

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
class RecoveryKeyViewInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var navController: TestNavHostController // NavController del Test

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
        //Hace clic en el botón Recuperar clave
        composeTestRule.onNodeWithTag(TestTags.LOGIN_RECOVER_KEY_BUTTON)
            .performClick()
        //Verifica si la navegación a Recuperar clave fue exitosa
        assertEquals(ViewRoutes.RecoverKey.name, navController.currentDestination?.route)
        //Escribe el email de usuario
        composeTestRule.onNodeWithTag(TestTags.RECOVER_EMAIL_FIELD)
            .performTextInput("2010guabo@gmail.com")
        //Clic en el botón Enviar código
        composeTestRule.onNodeWithTag(TestTags.RECOVER_SEND_CODE_BUTTON)
            .performClick()
        //Escribe el código de verificación en el AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_INPUT_CODE)
            .performTextInput(code)
        //Clic en el botón Verificar del AlertDialog
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_CODE_CHECK)
            .performClick()
        //Escribe nueva contraseña
        composeTestRule.onNodeWithTag(TestTags.RECOVER_PASSWORD_FIELD)
            .performTextInput("Yjot2025")
        //Clic en el botón Cambiar contraseña
        composeTestRule.onNodeWithTag(TestTags.RECOVER_CHANGE_PASSWORD_BUTTON)
            .performClick()
    }
}