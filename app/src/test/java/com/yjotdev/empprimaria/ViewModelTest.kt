package com.yjotdev.empprimaria

import app.cash.turbine.test
import io.mockk.every
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.presentation.navigation.UiEvent
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase
import com.yjotdev.empprimaria.domain.usecase.user.SetPasswordRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.GetRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.GetLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertLocalUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateLocalUserUseCase
import com.yjotdev.empprimaria.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.empprimaria.presentation.navigation.ViewRoutes
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
class ViewModelTest {

    @RelaxedMockK
    private lateinit var stringUseCase: StringUseCase

    @RelaxedMockK
    private lateinit var getRemoteUserUseCase: GetRemoteUserUseCase

    @RelaxedMockK
    private lateinit var getLocalUserUseCase: GetLocalUserUseCase

    @RelaxedMockK
    private lateinit var insertRemoteUserUseCase: InsertRemoteUserUseCase

    @RelaxedMockK
    private lateinit var insertLocalUserUseCase: InsertLocalUserUseCase

    @RelaxedMockK
    private lateinit var updateRemoteUserUseCase: UpdateRemoteUserUseCase

    @RelaxedMockK
    private lateinit var updateLocalUserUseCase: UpdateLocalUserUseCase

    @RelaxedMockK
    private lateinit var setPasswordRemoteUserUseCase: SetPasswordRemoteUserUseCase

    @RelaxedMockK
    private lateinit var deleteRemoteUserUseCase: DeleteRemoteUserUseCase

    @RelaxedMockK
    private lateinit var deleteLocalUserUseCase: DeleteLocalUserUseCase

    @RelaxedMockK
    private lateinit var sendEmailUseCase: SendEmailUseCase

    private lateinit var viewModel: UiViewModel
    private val testDispatcher = StandardTestDispatcher()
    private var localUserFlow = MutableStateFlow(UserModel())

    @Before
    fun setUp() {
        // Inicializa los mocks anotados en esta clase.
        MockKAnnotations.init(this)
        // Establece el dispatcher de prueba como el principal para controlar las corutinas.
        Dispatchers.setMain(testDispatcher)
        // Configura el comportamiento de los mocks.
        localUserFlow = MutableStateFlow(UserModel())
        every { getLocalUserUseCase() } returns localUserFlow
        // Crea la instancia del ViewModel con los mocks.
        viewModel = UiViewModel(
            stringUseCase,
            getRemoteUserUseCase,
            getLocalUserUseCase,
            insertRemoteUserUseCase,
            insertLocalUserUseCase,
            updateRemoteUserUseCase,
            updateLocalUserUseCase,
            setPasswordRemoteUserUseCase,
            deleteRemoteUserUseCase,
            deleteLocalUserUseCase,
            sendEmailUseCase
        )
    }

    @After
    fun tearDown() {
        // Restablece el dispatcher principal a su estado original.
        Dispatchers.resetMain()
        // Limpia todos los mocks y sus configuraciones.
        unmockkAll()
    }

    @Test
    fun whenLoginUserIsSuccessfulThenUiStateIsUpdatedAndNavigateEventIsSent() = runTest {
        // Given
        val fakeUserModel = UserModel(1, "testUser", "test@test.com", "Pass123")
        val loginModel = LoginModel("testUser", "Pass123")
        coEvery { getRemoteUserUseCase(loginModel) } returns Result.Success(fakeUserModel)
        coEvery { insertLocalUserUseCase(fakeUserModel) } answers {
            localUserFlow.value = fakeUserModel
        }

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.Navigate(
                    ViewRoutes.UserInfo.name,
                    ViewRoutes.Login.name
                ), awaitItem())
            }
        }
        viewModel.loginUser(loginModel.name, loginModel.password)
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertEquals(fakeUserModel, viewModel.uiState.value.user)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteUserUseCase(loginModel) }
        coVerify(exactly = 1) { insertLocalUserUseCase(fakeUserModel) }
    }

    @Test
    fun whenLoginUserFailsThenShowLogEventIsSent() = runTest {
        // Given
        val fakeUserLocal = UserModel(
            id = 0,
            name = "",
            email = "",
            password = "",
            isInvited = false,
            isInWhiteList = false
        )
        val loginModel = LoginModel("testUser", "Pass123")
        val exception = Exception("Incorrect user")
        every { getLocalUserUseCase() } returns flowOf(fakeUserLocal)
        coEvery { getRemoteUserUseCase(loginModel) } returns Result.Error(exception)

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        viewModel.loginUser(loginModel.name, loginModel.password)
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertEquals(UserModel(), viewModel.uiState.value.user)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteUserUseCase(loginModel) }
    }

    @Test
    fun whenInsertUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToInsert = UserModel(0, "newUser", "new@test.com", "Pass123")

        coEvery { insertRemoteUserUseCase(userToInsert) } returns Result.Success(Unit)
        coEvery { stringUseCase(R.string.alert_user_registered) } returns "User registered successfully"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("User registered successfully"), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.insertUser(
            userToInsert.name,
            userToInsert.email,
            userToInsert.password
        )
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { insertRemoteUserUseCase(userToInsert) }
        coVerify(exactly = 1) { stringUseCase(R.string.alert_user_registered) }
    }

    @Test
    fun whenInsertUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToInsert = UserModel(0, "newUser", "new@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { insertRemoteUserUseCase(userToInsert) } returns Result.Error(exception)
        coEvery { stringUseCase(R.string.error_user_registered) } returns "Error registering user"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Error registering user"), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.insertUser(
            userToInsert.name,
            userToInsert.email,
            userToInsert.password
        )
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { insertRemoteUserUseCase(userToInsert) }
        coVerify(exactly = 1) { stringUseCase(R.string.error_user_registered) }
    }

    @Test
    fun whenUpdateUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToUpdate = UserModel(1, "testUser", "test@test.com", "Pass123")
        coEvery { updateRemoteUserUseCase(userToUpdate.id, userToUpdate) } returns Result.Success(Unit)
        coEvery { stringUseCase(R.string.alert_user_updated) } returns "User updated successfully"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("User updated successfully"), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.updateUser(
            userToUpdate.id,
            userToUpdate.name,
            userToUpdate.email,
            userToUpdate.password,
            userToUpdate.photo
        )
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { updateRemoteUserUseCase(userToUpdate.id, userToUpdate) }
        coVerify(exactly = 1) { stringUseCase(R.string.alert_user_updated) }
    }

    @Test
    fun whenUpdateUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToUpdate = UserModel(1, "testUser", "test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { updateRemoteUserUseCase(userToUpdate.id, userToUpdate) } returns Result.Error(exception)
        coEvery { stringUseCase(R.string.error_user_updated) } returns "Error updating user"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Error updating user"), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.updateUser(
            userToUpdate.id,
            userToUpdate.name,
            userToUpdate.email,
            userToUpdate.password,
            userToUpdate.photo
        )
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { updateRemoteUserUseCase(userToUpdate.id, userToUpdate) }
        coVerify(exactly = 1) { stringUseCase(R.string.error_user_updated) }
    }

    @Test
    fun whenChangePasswordIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToUpdate = RecoveryModel("test@test.com", "Pass123")
        coEvery { setPasswordRemoteUserUseCase(userToUpdate) } returns Result.Success(Unit)
        coEvery { stringUseCase(R.string.alert_user_changed_password) } returns "Password updated successfully"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Password updated successfully"), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.changePassword(userToUpdate.email, userToUpdate.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { setPasswordRemoteUserUseCase(userToUpdate) }
        coVerify(exactly = 1) { stringUseCase(R.string.alert_user_changed_password) }
    }

    @Test
    fun whenChangePasswordFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToUpdate = RecoveryModel("test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { setPasswordRemoteUserUseCase(userToUpdate) } returns Result.Error(exception)
        coEvery { stringUseCase(R.string.error_user_changed_password) } returns "Error changing password"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Error changing password"), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.changePassword(userToUpdate.email, userToUpdate.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { setPasswordRemoteUserUseCase(userToUpdate) }
        coVerify(exactly = 1) { stringUseCase(R.string.error_user_changed_password) }
    }

    @Test
    fun whenDeleteUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToDelete = UserModel(1, "testUser", "test@test.com", "Pass123")
        coEvery { deleteRemoteUserUseCase(userToDelete.id) } returns Result.Success(Unit)
        coEvery { stringUseCase(R.string.alert_user_deleted) } returns "User deleted successfully"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("User deleted successfully"), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.deleteUser(userToDelete.id)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { deleteRemoteUserUseCase(userToDelete.id) }
        coVerify(exactly = 1) { stringUseCase(R.string.alert_user_deleted) }
    }

    @Test
    fun whenDeleteUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToDelete = UserModel(1, "testUser", "test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { deleteRemoteUserUseCase(userToDelete.id) } returns Result.Error(exception)
        coEvery { stringUseCase(R.string.error_user_deleted) } returns "Error deleting user"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Error deleting user"), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.deleteUser(userToDelete.id)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { deleteRemoteUserUseCase(userToDelete.id) }
        coVerify(exactly = 1) { stringUseCase(R.string.error_user_deleted) }
    }

    @Test
    fun whenSendEmailIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val email = EmailModel(
            to = "test@test.com",
            subject = "Test Subject",
            text = "Test Body"
        )
        coEvery { sendEmailUseCase(email) } returns Result.Success(Unit)
        coEvery { stringUseCase(R.string.alert_code_sent) } returns "Email sent successfully"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Email sent successfully"), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.sendEmail(email.to, email.subject, email.text)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { sendEmailUseCase(email) }
        coVerify(exactly = 1) { stringUseCase(R.string.alert_code_sent) }
    }

    @Test
    fun whenSendEmailFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val email = EmailModel(
            to = "test@test.com",
            subject = "Test Subject",
            text = "Test Body"
        )
        val exception = Exception("Email error")
        coEvery { sendEmailUseCase(email) } returns Result.Error(exception)
        coEvery { stringUseCase(R.string.error_code_sent) } returns "Error sending email"

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast("Error sending email"), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.sendEmail(email.to, email.subject, email.text)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { sendEmailUseCase(email) }
        coVerify(exactly = 1) { stringUseCase(R.string.error_code_sent) }
    }

    @Test
    fun setExperienceUpdatesUiStateCorrectly() = runTest {
        // Given
        val newExperience = 100

        // Then
        viewModel.uiState.test {
            assertEquals(0, awaitItem().experience) // Initial state

            // When
            viewModel.setExperience(newExperience)

            assertEquals(newExperience, awaitItem().experience) // Updated state
        }
    }

    @Test
    fun setTimeSpentUpdatesUiStateCorrectly() = runTest {
        // Given
        val newTimeSpent = 50

        // Then
        viewModel.uiState.test {
            assertEquals(0, awaitItem().timeSpent) // Initial state

            // When
            viewModel.setTimeSpent(newTimeSpent)

            assertEquals(newTimeSpent, awaitItem().timeSpent) // Updated state
        }
    }

    @Test
    fun setCourseCompletedUpdatesUiStateCorrectly() = runTest {
        // Given
        val newCourseCompleted = 75

        // Then
        viewModel.uiState.test {
            assertEquals(0, awaitItem().courseCompleted) // Initial state

            // When
            viewModel.setCourseCompleted(newCourseCompleted)

            assertEquals(newCourseCompleted, awaitItem().courseCompleted) // Updated state
        }
    }
}