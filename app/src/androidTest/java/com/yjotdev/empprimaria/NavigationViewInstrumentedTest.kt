package com.yjotdev.empprimaria

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Before
import javax.inject.Inject
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import com.yjotdev.empprimaria.application.navigation.PermissionView
import com.yjotdev.empprimaria.application.navigation.ViewRoutes
import com.yjotdev.empprimaria.application.theme.EmprendimientoPrimariaTheme

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationViewInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navController: TestNavHostController // NavController del Test

    @Before
    fun setup() {
        hiltRule.inject() // Inicializa Hilt
    }
    // Contexto del test de la app.
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun navigateLoginToMenu() {
        composeTestRule.setContent {
            EmprendimientoPrimariaTheme {
                PermissionView(navController)
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
        composeTestRule.waitUntil(30000L) {
            navController.currentDestination?.route == ViewRoutes.UserInfo.name
        }
        //Verifica si la navegacion a UserInfo fue exitosa
        assertEquals(ViewRoutes.UserInfo.name, navController.currentDestination?.route)
    }

    @Test
    fun navigateLoginToRegister() {
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
    }

    @Test
    fun navigationLoginToRecoverKey() {
        composeTestRule.setContent {
            EmprendimientoPrimariaTheme {
                PermissionView(navController)
            }
        }
        //Hace click en el boton Recuperar clave
        composeTestRule.onNodeWithText(
            context.getString(R.string.button_recover_key)
        ).performClick()
        //Verifica si la navegacion a Recuperar clave fue exitosa
        assertEquals(ViewRoutes.RecoverKey.name, navController.currentDestination?.route)
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
    fun navigationMenuToEducationalModules() {
        navigateLoginToMenu()
        //Hace click en el boton para ver los modulos educativos
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.button_units)
        ).performClick()
        //Verifica que esta en la vista de los modulos educativos
        composeTestRule.onNodeWithText(
            context.getString(R.string.unit, "1")
        ).assertExists()
    }

    @Test
    fun navigationMenuToPracticalProjects() {
        navigateLoginToMenu()
        //Hace click en el boton para ver los proyectos practicos
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.button_projects)
        ).performClick()
        //Verifica que esta en la vista de los proyectos practicos
        composeTestRule.onNodeWithText("Revista Escolar")
            .assertExists()
    }

    @Test
    fun navigationMenuToTrackingAndSupport() {
        navigateLoginToMenu()
        //Hace click en el boton para ver el seguimiento y soporte
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.button_opinion)
        ).performClick()
        //Verifica que esta en la vista del seguimiento y soporte
        composeTestRule.onNodeWithText(
            context.getString(R.string.my_progress)
        ).assertExists()
    }
}