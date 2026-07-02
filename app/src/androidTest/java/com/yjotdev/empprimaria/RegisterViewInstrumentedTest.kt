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
class RegisterViewInstrumentedTest {

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
    fun createUser_RegisterView() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            EmprendimientoPrimariaTheme {
                PermissionView(navController = navController)
            }
        }
        //Hace clic en el botón Registrarse
        composeTestRule.onNodeWithTag(TestTags.LOGIN_REGISTER_BUTTON)
            .performClick()
        //Verifica si la navegación a Registrarse fue exitosa
        assertEquals(ViewRoutes.Register.name, navController.currentDestination?.route)
        //Escribe nombre de usuario
        composeTestRule.onNodeWithTag(TestTags.REGISTER_USER_FIELD)
            .performTextInput("juan")
        //Escribe email de usuario
        composeTestRule.onNodeWithTag(TestTags.REGISTER_EMAIL_FIELD)
            .performTextInput("juan@gmail.com")
        //Escribe clave de usuario
        composeTestRule.onNodeWithTag(TestTags.REGISTER_PASSWORD_FIELD)
            .performTextInput("Juan0001")
        //Hace clic en el botón Registrarse
        composeTestRule.onNodeWithTag(TestTags.REGISTER_SUBMIT_BUTTON)
            .performClick()
    }
}