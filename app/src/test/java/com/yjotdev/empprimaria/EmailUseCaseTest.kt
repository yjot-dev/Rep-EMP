package com.yjotdev.empprimaria

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailPort
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase

/**
 * Pruebas unitarias para los casos de uso de Email.
 */
class EmailUseCaseTest {

    private lateinit var emailPort: EmailPort

    private lateinit var sendEmailUseCase: SendEmailUseCase

    @Before
    fun setUp() {
        emailPort = mockk()
        sendEmailUseCase = SendEmailUseCase(emailPort)
    }

    @Test
    fun whenSendEmailUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val email = EmailEntity("to@example.com", "Subject", "Body")
        coEvery { emailPort.sendEmail(email) } returns Result.Success(Unit)

        // When
        sendEmailUseCase(email)

        // Then
        coVerify(exactly = 1) { emailPort.sendEmail(email) }
    }
}