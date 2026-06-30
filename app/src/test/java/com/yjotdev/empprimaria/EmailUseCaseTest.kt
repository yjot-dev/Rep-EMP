package com.yjotdev.empprimaria

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.domain.core.Result
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.usecase.email.SendEmailUseCase

/**
 * Pruebas unitarias para los casos de uso de Email.
 */
class EmailUseCaseTest {

    private lateinit var emailRepository: EmailRepository

    private lateinit var sendEmailUseCase: SendEmailUseCase

    @Before
    fun setUp() {
        emailRepository = mockk()
        sendEmailUseCase = SendEmailUseCase(emailRepository)
    }

    @Test
    fun whenSendEmailUseCaseIsInvokedSuccessfullyThenPortMethodIsCalled() = runTest {
        // Given
        val email = EmailModel("to@example.com", "Subject", "Body")
        coEvery { emailRepository.sendEmail(email) } returns Result.Success(Unit)

        // When
        val result = sendEmailUseCase(email)

        // Then
        assert(result is Result.Success)
        assert(Unit == (result as Result.Success).data)
        coVerify(exactly = 1) { emailRepository.sendEmail(email) }
    }

    @Test
    fun whenSendEmailUseCaseIsInvokedWithErrorThenTheExceptionIsCalled() = runTest {
        // Given
        val email = EmailModel("to@example.com", "Subject", "Body")
        val exception = Exception("Error enviando email")
        coEvery { emailRepository.sendEmail(email) } returns Result.Error(exception)

        // When
        val result = sendEmailUseCase(email)

        // Then
        assert(result is Result.Error)
        assert(exception == (result as Result.Error).exception)
        coVerify(exactly = 1) { emailRepository.sendEmail(email) }
    }
}