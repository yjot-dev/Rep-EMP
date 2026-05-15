package com.yjotdev.empprimaria

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.domain.repository.StringRepository
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase

/**
 * Pruebas unitarias para el caso de uso de String.
 */
class StringUseCaseTest {

    private lateinit var stringRepository: StringRepository
    private lateinit var stringUseCase: StringUseCase

    @Before
    fun setUp() {
        stringRepository = mockk()
        stringUseCase = StringUseCase(stringRepository)
    }

    @Test
    fun whenGetStringWithResourceIdIsInvokedThenItReturnsTheCorrectString() {
        // Given
        val resId = 123
        val expectedString = "Hello"
        every { stringRepository.getString(resId) } returns expectedString

        // When
        val result = stringUseCase(resId)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringRepository.getString(resId) }
    }

    @Test
    fun whenGetStringWithResourceIdAndArgsIsInvokedThenItReturnsTheCorrectFormattedString() {
        // Given
        val resId = 456
        val arg1 = "World"
        val expectedString = "Hello World"
        every { stringRepository.getString(resId, any()) } returns expectedString

        // When
        val result = stringUseCase(resId, arg1)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringRepository.getString(resId, any()) }
    }
}
