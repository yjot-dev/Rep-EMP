package com.yjotdev.empprimaria

import app.cash.turbine.test
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
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.application.navigation.UiEvent
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel
import com.yjotdev.empprimaria.application.navigation.ViewRoutes
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity

@ExperimentalCoroutinesApi
class ViewModelTest {

    @RelaxedMockK
    private lateinit var getString: StringUseCase

    @RelaxedMockK
    private lateinit var findUserUseCase: FindUserUseCase

    @RelaxedMockK
    private lateinit var insertUserUseCase: InsertUserUseCase

    @RelaxedMockK
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @RelaxedMockK
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase

    @RelaxedMockK
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @RelaxedMockK
    private lateinit var sendEmailUseCase: SendEmailUseCase

    private lateinit var viewModel: ProgressViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Inicializa los mocks anotados en esta clase.
        MockKAnnotations.init(this)
        // Establece el dispatcher de prueba como el principal para controlar las corutinas.
        Dispatchers.setMain(testDispatcher)
        // Crea la instancia del ViewModel con los mocks.
        viewModel = ProgressViewModel(
            getString,
            findUserUseCase,
            insertUserUseCase,
            updateUserUseCase,
            changePasswordUserUseCase,
            deleteUserUseCase,
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
        val fakeUserEntity = UserEntity(1, "testUser", "test@test.com", "Pass123")
        val loginEntity = LoginEntity("testUser", "Pass123")
        coEvery { findUserUseCase(loginEntity) } returns Result.Success(fakeUserEntity)

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.Navigate(
                    ViewRoutes.UserInfo.name,
                    ViewRoutes.Login.name
                ), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
                assertEquals(fakeUserEntity, successState.user)
            }
        }

        // When
        viewModel.loginUser(loginEntity.name, loginEntity.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { findUserUseCase(loginEntity) }
    }

    @Test
    fun whenLoginUserFailsThenShowLogEventIsSent() = runTest {
        // Given
        val loginEntity = LoginEntity("testUser", "Pass123")
        val exception = Exception("Incorrect user")
        coEvery { findUserUseCase(loginEntity) } returns Result.Error(exception)

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
                assertEquals(UserEntity(), errorState.user)
            }
        }

        // When
        viewModel.loginUser(
            loginEntity.name,
            loginEntity.password
        )
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { findUserUseCase(loginEntity) }
    }

    @Test
    fun whenInsertUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToInsert = UserEntity(0, "newUser", "new@test.com", "Pass123")

        coEvery { insertUserUseCase(userToInsert) } returns Result.Success(Unit)
        coEvery { getString(R.string.alert_user_registered) } returns "User registered successfully"

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

        coVerify(exactly = 1) { insertUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getString(R.string.alert_user_registered) }
    }

    @Test
    fun whenInsertUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToInsert = UserEntity(0, "newUser", "new@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { insertUserUseCase(userToInsert) } returns Result.Error(exception)
        coEvery { getString(R.string.error_user_registered) } returns "Error registering user"

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

        coVerify(exactly = 1) { insertUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getString(R.string.error_user_registered) }
    }

    @Test
    fun whenUpdateUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToUpdate = UserEntity(1, "testUser", "test@test.com", "Pass123")
        coEvery { updateUserUseCase(userToUpdate.id, userToUpdate) } returns Result.Success(Unit)
        coEvery { getString(R.string.alert_user_updated) } returns "User updated successfully"

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

        coVerify(exactly = 1) { updateUserUseCase(userToUpdate.id, userToUpdate) }
        coVerify(exactly = 1) { getString(R.string.alert_user_updated) }
    }

    @Test
    fun whenUpdateUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToUpdate = UserEntity(1, "testUser", "test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { updateUserUseCase(userToUpdate.id, userToUpdate) } returns Result.Error(exception)
        coEvery { getString(R.string.error_user_updated) } returns "Error updating user"

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

        coVerify(exactly = 1) { updateUserUseCase(userToUpdate.id, userToUpdate) }
        coVerify(exactly = 1) { getString(R.string.error_user_updated) }
    }

    @Test
    fun whenChangePasswordIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToUpdate = RecoveryEntity("test@test.com", "Pass123")
        coEvery { changePasswordUserUseCase(userToUpdate) } returns Result.Success(Unit)
        coEvery { getString(R.string.alert_user_changed_password) } returns "Password updated successfully"

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

        coVerify(exactly = 1) { changePasswordUserUseCase(userToUpdate) }
        coVerify(exactly = 1) { getString(R.string.alert_user_changed_password) }
    }

    @Test
    fun whenChangePasswordFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToUpdate = RecoveryEntity("test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { changePasswordUserUseCase(userToUpdate) } returns Result.Error(exception)
        coEvery { getString(R.string.error_user_changed_password) } returns "Error changing password"

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

        coVerify(exactly = 1) { changePasswordUserUseCase(userToUpdate) }
        coVerify(exactly = 1) { getString(R.string.error_user_changed_password) }
    }

    @Test
    fun whenDeleteUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToDelete = UserEntity(1, "testUser", "test@test.com", "Pass123")
        coEvery { deleteUserUseCase(userToDelete.id) } returns Result.Success(Unit)
        coEvery { getString(R.string.alert_user_deleted) } returns "User deleted successfully"

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

        coVerify(exactly = 1) { deleteUserUseCase(userToDelete.id) }
        coVerify(exactly = 1) { getString(R.string.alert_user_deleted) }
    }

    @Test
    fun whenDeleteUserFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val userToDelete = UserEntity(1, "testUser", "test@test.com", "Pass123")
        val exception = Exception("DB error")
        coEvery { deleteUserUseCase(userToDelete.id) } returns Result.Error(exception)
        coEvery { getString(R.string.error_user_deleted) } returns "Error deleting user"

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

        coVerify(exactly = 1) { deleteUserUseCase(userToDelete.id) }
        coVerify(exactly = 1) { getString(R.string.error_user_deleted) }
    }

    @Test
    fun whenSendEmailIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val email = EmailEntity(
            to = "test@test.com",
            subject = "Test Subject",
            text = "Test Body"
        )
        coEvery { sendEmailUseCase(email) } returns Result.Success(Unit)
        coEvery { getString(R.string.alert_code_sent) } returns "Email sent successfully"

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
        coVerify(exactly = 1) { getString(R.string.alert_code_sent) }
    }

    @Test
    fun whenSendEmailFailsThenShowToastAndLogEventsAreSent() = runTest {
        // Given
        val email = EmailEntity(
            to = "test@test.com",
            subject = "Test Subject",
            text = "Test Body"
        )
        val exception = Exception("Email error")
        coEvery { sendEmailUseCase(email) } returns Result.Error(exception)
        coEvery { getString(R.string.error_code_sent) } returns "Error sending email"

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
        coVerify(exactly = 1) { getString(R.string.error_code_sent) }
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