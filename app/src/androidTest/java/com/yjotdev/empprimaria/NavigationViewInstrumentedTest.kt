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
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Before
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import com.yjotdev.empprimaria.presentation.navigation.PermissionView
import com.yjotdev.empprimaria.presentation.navigation.ViewRoutes
import com.yjotdev.empprimaria.presentation.theme.EmprendimientoPrimariaTheme
import com.yjotdev.empprimaria.presentation.utils.TestTags

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationViewInstrumentedTest {

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
    fun navigateLoginToMenu() {
        loadTestActivity()
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
    fun navigateLoginToRegister() {
        loadTestActivity()
        //Hace clic en el botón Registrarse
        composeTestRule.onNodeWithTag(TestTags.LOGIN_REGISTER_BUTTON)
            .performClick()
        //Verifica si la navegación a Registrarse fue exitosa
        assertEquals(ViewRoutes.Register.name, navController.currentDestination?.route)
    }

    @Test
    fun navigationLoginToRecoverKey() {
        loadTestActivity()
        //Hace clic en el botón Recuperar clave
        composeTestRule.onNodeWithTag(TestTags.LOGIN_RECOVER_KEY_BUTTON)
            .performClick()
        //Verifica si la navegación a Recuperar clave fue exitosa
        assertEquals(ViewRoutes.RecoverKey.name, navController.currentDestination?.route)
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
    fun navigationMenuToEducationalModules() {
        navigateLoginToMenu()
        //Hace clic en el botón para ver los módulos educativos
        composeTestRule.onNodeWithTag(TestTags.TOP_BAR_UNITS)
            .performClick()
        //Verifica que está en la vista de los módulos educativos
        composeTestRule.onNodeWithTag(TestTags.UNIT_TITLE)
            .assertExists()
    }

    @Test
    fun navigationMenuToPracticalProjects() {
        navigateLoginToMenu()
        //Hace clic en el botón para ver los proyectos prácticos
        composeTestRule.onNodeWithTag(TestTags.TOP_BAR_PROJECTS)
            .performClick()
        //Verifica que está en la vista de los proyectos prácticos
        composeTestRule.onNodeWithTag(TestTags.PROJECT_ITEM)
            .assertExists()
    }

    @Test
    fun navigationMenuToTrackingAndSupport() {
        navigateLoginToMenu()
        //Hace clic en el botón para ver el seguimiento y soporte
        composeTestRule.onNodeWithTag(TestTags.TOP_BAR_OPINION)
            .performClick()
        //Verifica que está en la vista del seguimiento y soporte
        composeTestRule.onNodeWithTag(TestTags.OPINION_TITLE)
            .assertExists()
    }

    /**
     * Función para cargar la vista de prueba.
     * Evita errores de aserción inmediata antes de que la vista se cargue.
     */
    private fun loadTestActivity() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            EmprendimientoPrimariaTheme {
                PermissionView(navController = navController)
            }
        }
    }
}