package com.yjotdev.empprimaria

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.application.mvvm.viewmodel.ProgressViewModel
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.usecase.email.SendCommentaryUseCase
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class ProgressViewModelTest {

    // Dependencias Mockeadas
    private val findUserUseCase: FindUserUseCase = mockk()
    private val insertUserUseCase: InsertUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk()
    private val changePasswordUserUseCase: ChangePasswordUserUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    private val sendEmailUseCase: SendEmailUseCase = mockk()
    private val sendCommentaryUseCase: SendCommentaryUseCase = mockk()

    // ViewModel bajo prueba
    private lateinit var viewModel: ProgressViewModel

    // Configuración de Corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProgressViewModel(
            findUserUseCase,
            insertUserUseCase,
            updateUserUseCase,
            changePasswordUserUseCase,
            deleteUserUseCase,
            sendEmailUseCase,
            sendCommentaryUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setExperienceUpdatesStateCorrectly() = runTest {
        viewModel.setExperience(100)
        assertEquals(100, viewModel.uiState.value.experience)
    }

    @Test
    fun setUserInfoUpdatesStateCorrectly() = runTest {
        val user = UserEntity(id = 1, name = "Test", email = "test@test.com")
        viewModel.setUserInfo(user)

        assertEquals("Test", viewModel.userInfo.value.name)
        assertEquals("test@test.com", viewModel.userInfo.value.email)
    }

    @Test
    fun resetViewModelClearsAllStates() = runTest {
        // Given: Estado sucio
        viewModel.setExperience(50)
        viewModel.setUserInfo(UserEntity(name = "Dirty"))

        // When
        viewModel.resetViewModel()

        // Then
        assertEquals(0, viewModel.uiState.value.experience)
        assertEquals("", viewModel.userInfo.value.name)
    }

    @Test
    fun findUserSuccessUpdatesStateWithUser() = runTest {
        // Given
        val mockUser = UserEntity(id = 1, name = "Found", email = "found@email.com")
        coEvery { findUserUseCase(any()) } returns Result.Success(mockUser)

        viewModel.uiState.test {
            // Estado inicial
            awaitItem()

            // When
            viewModel.findUser("Found", "pass")

            // Then: Loading true
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // Then: Success
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.wasFound)
            assertEquals(mockUser, successState.user)
            assertEquals(1, successState.operationCompletedCount)
        }
    }

    @Test
    fun findUserErrorUpdatesStateWithErrorMessage() = runTest {
        // Given
        val errorMessage = "User not found"
        coEvery { findUserUseCase(any()) } returns Result.Error(Exception(errorMessage))

        viewModel.uiState.test {
            awaitItem() // Initial

            // When
            viewModel.findUser("Wrong","pass")

            // Then
            awaitItem() // Loading
            val errorState = awaitItem()

            assertFalse(errorState.isLoading)
            assertFalse(errorState.wasFound)
            assertNull(errorState.user)
            assertEquals(errorMessage, errorState.error)
        }
    }

    @Test
    fun insertUserCallsUseCaseAndUpdatesWasInserted() = runTest {
        // Given
        coEvery { insertUserUseCase(any()) } returns Result.Success(Unit)

        viewModel.uiState.test {
            awaitItem()

            // When
            viewModel.insertUser("New", "new@email.com", "1234")

            // Then
            awaitItem() // Loading
            val successState = awaitItem()

            assertTrue(successState.wasInserted)

            // Verify use case was called with correct data
            coVerify {
                insertUserUseCase(match {
                    it.name == "New" && it.email == "new@email.com"
                })
            }
        }
    }

    @Test
    fun updateUserSuccessUpdatesWasUpdated() = runTest {
        // Given
        coEvery { updateUserUseCase(any(), any()) } returns Result.Success(Unit)

        viewModel.uiState.test {
            awaitItem()

            // When
            viewModel.updateUser(1, "Updated", "up@email.com", "newpass", "")

            // Then
            awaitItem() // Loading
            val successState = awaitItem()

            assertTrue(successState.wasUpdated)
        }
    }

    @Test
    fun sendCodeByEmailSuccessUpdatesWasEmailed() = runTest {
        // Given
        coEvery { sendEmailUseCase(any()) } returns Result.Success(Unit)

        viewModel.uiState.test {
            awaitItem()

            // When
            viewModel.sendCodeByEmail("to@email.com", "Subject", "Code 123")

            // Then
            awaitItem() // Loading
            val successState = awaitItem()

            assertTrue(successState.wasEmailed)

            coVerify {
                sendEmailUseCase(match {
                    it.to == "to@email.com" && it.text == "Code 123"
                })
            }
        }
    }

    @Test
    fun clearFlagsResetsAllBooleanFlags() = runTest {
        // Given: Estado con flags en true (simulado manual o por llamadas previas)
        // Forzamos un estado sucio simulando una respuesta exitosa previa, o manipulando el state si fuera publico
        // Como _uiState es privado, usamos métodos públicos que sabemos que cambian flags

        // Simulamos un insert exitoso para poner wasInserted = true
        coEvery { insertUserUseCase(any()) } returns Result.Success(Unit)
        viewModel.insertUser("A", "B", "C")

        // Avanzamos corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.wasInserted)

        // When
        viewModel.clearFlags()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.wasFound)
        assertFalse(state.wasInserted)
        assertFalse(state.wasUpdated)
        assertFalse(state.wasDeleted)
        assertFalse(state.wasEmailed)
    }
}