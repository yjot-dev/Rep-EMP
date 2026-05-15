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
import com.yjotdev.empprimaria.domain.repository.UserRepository
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase

/**
 * Pruebas unitarias para los casos de uso CRUD de Usuarios.
 */
class UserUseCaseTest {

    private lateinit var userRepository: UserRepository

    private lateinit var findUserUseCase: FindUserUseCase
    private lateinit var insertUserUseCase: InsertUserUseCase
    private lateinit var updateUserUseCase: UpdateUserUseCase
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        findUserUseCase = FindUserUseCase(userRepository)
        insertUserUseCase = InsertUserUseCase(userRepository)
        updateUserUseCase = UpdateUserUseCase(userRepository)
        changePasswordUserUseCase = ChangePasswordUserUseCase(userRepository)
        deleteUserUseCase = DeleteUserUseCase(userRepository)
    }

    @Test
    fun whenFindUserUseCaseIsInvokedSuccessfullyThenItReturnsAUser() = runTest {
        // Given
        val loginModel = LoginModel("testuser", "password")
        val fakeUser = UserModel(id = 1, name = "Test User", email = "test@example.com", password = "password")
        coEvery { userRepository.findUser(loginModel) } returns Result.Success(fakeUser)

        // When
        val result = findUserUseCase(loginModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeUser, (result as Result.Success).data)
        coVerify(exactly = 1) { userRepository.findUser(loginModel) }
    }

    @Test
    fun whenInsertUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val newUser = UserModel(id = 0, name = "New User", email = "new@example.com", password = "newpassword")
        coEvery { userRepository.insertUser(newUser) } returns Result.Success(Unit)

        // When
        insertUserUseCase(newUser)

        // Then
        coVerify(exactly = 1) { userRepository.insertUser(newUser) }
    }

    @Test
    fun whenUpdateUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userToUpdate = UserModel(id = 1, name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        coEvery { userRepository.updateUser(userToUpdate.id, userToUpdate) } returns Result.Success(Unit)

        // When
        updateUserUseCase(userToUpdate.id, userToUpdate)

        // Then
        coVerify(exactly = 1) { userRepository.updateUser(userToUpdate.id, userToUpdate) }
    }

    @Test
    fun whenChangePasswordUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@example.com", password = "newpassword")
        coEvery { userRepository.changePasswordUser(recoveryModel) } returns Result.Success(Unit)

        // When
        changePasswordUserUseCase(recoveryModel)

        // Then
        coVerify(exactly = 1) { userRepository.changePasswordUser(recoveryModel) }
    }

    @Test
    fun whenDeleteUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userId = 1
        coEvery { userRepository.deleteUser(userId) } returns Result.Success(Unit)

        // When
        deleteUserUseCase(userId)

        // Then
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }
}