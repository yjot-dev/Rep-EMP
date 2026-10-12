package com.yjotdev.empprimaria

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.LoginModel
import com.yjotdev.empprimaria.domain.model.RecoveryModel
import com.yjotdev.empprimaria.domain.model.UserModel
import com.yjotdev.empprimaria.domain.repository.UserApiRepository
import com.yjotdev.empprimaria.domain.usecase.user.SetPasswordRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.GetRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertRemoteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateRemoteUserUseCase

/**
 * Pruebas unitarias para los casos de uso CRUD de Usuarios.
 */
class UserUseCaseTest {

    private lateinit var userApiRepository: UserApiRepository

    private lateinit var getRemoteUserUseCase: GetRemoteUserUseCase
    private lateinit var insertRemoteUserUseCase: InsertRemoteUserUseCase
    private lateinit var updateRemoteUserUseCase: UpdateRemoteUserUseCase
    private lateinit var setPasswordRemoteUserUseCase: SetPasswordRemoteUserUseCase
    private lateinit var deleteRemoteUserUseCase: DeleteRemoteUserUseCase

    @Before
    fun setUp() {
        userApiRepository = mockk()
        getRemoteUserUseCase = GetRemoteUserUseCase(userApiRepository)
        insertRemoteUserUseCase = InsertRemoteUserUseCase(userApiRepository)
        updateRemoteUserUseCase = UpdateRemoteUserUseCase(userApiRepository)
        setPasswordRemoteUserUseCase = SetPasswordRemoteUserUseCase(userApiRepository)
        deleteRemoteUserUseCase = DeleteRemoteUserUseCase(userApiRepository)
    }

    @Test
    fun whenFindUserUseCaseIsInvokedSuccessfullyThenItReturnsAUser() = runTest {
        // Given
        val loginModel = LoginModel("testuser", "password")
        val fakeUser = UserModel(id = 1, name = "Test User", email = "test@example.com", password = "password")
        coEvery { userApiRepository.getRemoteUser(loginModel) } returns Result.Success(fakeUser)

        // When
        val result = getRemoteUserUseCase(loginModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeUser, (result as Result.Success).data)
        coVerify(exactly = 1) { userApiRepository.getRemoteUser(loginModel) }
    }

    @Test
    fun whenFindUserUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val loginModel = LoginModel("testuser", "password")
        val exception = Exception("Error al encontrar el usuario")
        coEvery { userApiRepository.getRemoteUser(loginModel) } returns Result.Error(exception)

        // When
        val result = getRemoteUserUseCase(loginModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userApiRepository.getRemoteUser(loginModel) }
    }

    @Test
    fun whenInsertUserUseCaseIsInvokedSuccessfullyThenPortMethodIsCalled() = runTest {
        // Given
        val newUser = UserModel(id = 0, name = "New User", email = "new@example.com", password = "newpassword")
        coEvery { userApiRepository.insertRemoteUser(newUser) } returns Result.Success(Unit)

        // When
        val result = insertRemoteUserUseCase(newUser)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { userApiRepository.insertRemoteUser(newUser) }
    }

    @Test
    fun whenInsertUserUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val newUser = UserModel(id = 0, name = "New User", email = "new@example.com", password = "newpassword")
        val exception = Exception("Error al insertar el usuario")
        coEvery { userApiRepository.insertRemoteUser(newUser) } returns Result.Error(exception)

        // When
        val result = insertRemoteUserUseCase(newUser)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userApiRepository.insertRemoteUser(newUser) }
    }

    @Test
    fun whenUpdateUserUseCaseIsInvokedSuccessfullyThenPortMethodIsCalled() = runTest {
        // Given
        val userToUpdate = UserModel(id = 1, name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        coEvery { userApiRepository.updateRemoteUser(userToUpdate.id, userToUpdate) } returns Result.Success(Unit)

        // When
        val result = updateRemoteUserUseCase(userToUpdate.id, userToUpdate)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { userApiRepository.updateRemoteUser(userToUpdate.id, userToUpdate) }
    }

    @Test
    fun whenUpdateUserUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val userToUpdate = UserModel(id = 1, name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        val exception = Exception("Error al actualizar el usuario")
        coEvery { userApiRepository.updateRemoteUser(userToUpdate.id, userToUpdate) } returns Result.Error(exception)

        // When
        val result = updateRemoteUserUseCase(userToUpdate.id, userToUpdate)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userApiRepository.updateRemoteUser(userToUpdate.id, userToUpdate) }
    }

    @Test
    fun whenChangePasswordUserUseCaseIsInvokedSuccessfullyThenPortMethodIsCalled() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@example.com", password = "newpassword")
        coEvery { userApiRepository.setPasswordRemoteUser(recoveryModel) } returns Result.Success(Unit)

        // When
        val result = setPasswordRemoteUserUseCase(recoveryModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { userApiRepository.setPasswordRemoteUser(recoveryModel) }
    }

    @Test
    fun whenChangePasswordUserUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@example.com", password = "newpassword")
        val exception = Exception("Error al cambiar la contraseña")
        coEvery { userApiRepository.setPasswordRemoteUser(recoveryModel) } returns Result.Error(exception)

        // When
        val result = setPasswordRemoteUserUseCase(recoveryModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userApiRepository.setPasswordRemoteUser(recoveryModel) }
    }

    @Test
    fun whenDeleteUserUseCaseIsInvokedSuccessfullyThenPortMethodIsCalled() = runTest {
        // Given
        val userId = 1
        coEvery { userApiRepository.deleteRemoteUser(userId) } returns Result.Success(Unit)

        // When
        val result = deleteRemoteUserUseCase(userId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { userApiRepository.deleteRemoteUser(userId) }
    }

    @Test
    fun whenDeleteUserUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al eliminar el usuario")
        coEvery { userApiRepository.deleteRemoteUser(userId) } returns Result.Error(exception)

        // When
        val result = deleteRemoteUserUseCase(userId)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userApiRepository.deleteRemoteUser(userId) }
    }
}