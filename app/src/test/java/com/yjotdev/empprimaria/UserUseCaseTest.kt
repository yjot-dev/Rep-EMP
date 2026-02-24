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
import com.yjotdev.empprimaria.domain.entity.LoginEntity
import com.yjotdev.empprimaria.domain.entity.RecoveryEntity
import com.yjotdev.empprimaria.domain.entity.UserEntity
import com.yjotdev.empprimaria.domain.port.UserPort
import com.yjotdev.empprimaria.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.FindUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.InsertUserUseCase
import com.yjotdev.empprimaria.domain.usecase.user.UpdateUserUseCase

/**
 * Pruebas unitarias para los casos de uso CRUD de Usuarios.
 */
class UserUseCaseTest {

    private lateinit var userPort: UserPort

    private lateinit var findUserUseCase: FindUserUseCase
    private lateinit var insertUserUseCase: InsertUserUseCase
    private lateinit var updateUserUseCase: UpdateUserUseCase
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        userPort = mockk()
        findUserUseCase = FindUserUseCase(userPort)
        insertUserUseCase = InsertUserUseCase(userPort)
        updateUserUseCase = UpdateUserUseCase(userPort)
        changePasswordUserUseCase = ChangePasswordUserUseCase(userPort)
        deleteUserUseCase = DeleteUserUseCase(userPort)
    }

    @Test
    fun whenFindUserUseCaseIsInvokedSuccessfullyThenItReturnsAUser() = runTest {
        // Given
        val loginEntity = LoginEntity("testuser", "password")
        val fakeUser = UserEntity(id = 1, name = "Test User", email = "test@example.com", password = "password")
        coEvery { userPort.findUser(loginEntity) } returns Result.Success(fakeUser)

        // When
        val result = findUserUseCase(loginEntity)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeUser, (result as Result.Success).data)
        coVerify(exactly = 1) { userPort.findUser(loginEntity) }
    }

    @Test
    fun whenInsertUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val newUser = UserEntity(id = 0, name = "New User", email = "new@example.com", password = "newpassword")
        coEvery { userPort.insertUser(newUser) } returns Result.Success(Unit)

        // When
        insertUserUseCase(newUser)

        // Then
        coVerify(exactly = 1) { userPort.insertUser(newUser) }
    }

    @Test
    fun whenUpdateUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userToUpdate = UserEntity(id = 1, name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        coEvery { userPort.updateUser(userToUpdate.id, userToUpdate) } returns Result.Success(Unit)

        // When
        updateUserUseCase(userToUpdate.id, userToUpdate)

        // Then
        coVerify(exactly = 1) { userPort.updateUser(userToUpdate.id, userToUpdate) }
    }

    @Test
    fun whenChangePasswordUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val recoveryEntity = RecoveryEntity(email = "test@example.com", password = "newpassword")
        coEvery { userPort.changePasswordUser(recoveryEntity) } returns Result.Success(Unit)

        // When
        changePasswordUserUseCase(recoveryEntity)

        // Then
        coVerify(exactly = 1) { userPort.changePasswordUser(recoveryEntity) }
    }

    @Test
    fun whenDeleteUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userId = 1
        coEvery { userPort.deleteUser(userId) } returns Result.Success(Unit)

        // When
        deleteUserUseCase(userId)

        // Then
        coVerify(exactly = 1) { userPort.deleteUser(userId) }
    }
}