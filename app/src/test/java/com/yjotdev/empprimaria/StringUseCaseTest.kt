package com.yjotdev.empprimaria

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.yjotdev.empprimaria.domain.port.StringPort
import com.yjotdev.empprimaria.domain.usecase.string.StringUseCase

/**
 * Pruebas unitarias para el caso de uso de String.
 */
class StringUseCaseTest {

    private lateinit var stringPort: StringPort
    private lateinit var stringUseCase: StringUseCase

    @Before
    fun setUp() {
        stringPort = mockk()
        stringUseCase = StringUseCase(stringPort)
    }

    @Test
    fun whenGetStringWithResourceIdIsInvokedThenItReturnsTheCorrectString() {
        // Given
        val resId = 123
        val expectedString = "Hello"
        every { stringPort.getString(resId) } returns expectedString

        // When
        val result = stringUseCase(resId)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringPort.getString(resId) }
    }

    @Test
    fun whenGetStringWithResourceIdAndArgsIsInvokedThenItReturnsTheCorrectFormattedString() {
        // Given
        val resId = 456
        val arg1 = "World"
        val expectedString = "Hello World"
        every { stringPort.getString(resId, any()) } returns expectedString

        // When
        val result = stringUseCase(resId, arg1)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringPort.getString(resId, any()) }
    }
}
