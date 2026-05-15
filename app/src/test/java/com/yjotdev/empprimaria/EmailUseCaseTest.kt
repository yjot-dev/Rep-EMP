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
    fun whenSendEmailUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val email = EmailModel("to@example.com", "Subject", "Body")
        coEvery { emailRepository.sendEmail(email) } returns Result.Success(Unit)

        // When
        sendEmailUseCase(email)

        // Then
        coVerify(exactly = 1) { emailRepository.sendEmail(email) }
    }
}