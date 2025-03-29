package com.yjotdev.empprimaria.mvvm.view.utils

import com.yjotdev.empprimaria.domain.utils.Validation
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateTextUnitTest {
    @Test
    fun validateMessage_test() {
        val case = Validation.validateText("Hola como estas", 0)
        assertEquals("", case)
    }

    @Test
    fun validateUserOrEmail_test() {
        val case1 = Validation.validateText("Yasser", 1)
        assertEquals("", case1)
        val case2 = Validation.validateText("2010guabo@gmail.com", 1)
        assertEquals("", case2)
    }

    @Test
    fun validateUser_test() {
        val case = Validation.validateText("Yasser", 2)
        assertEquals("", case)
    }

    @Test
    fun validateEmail_test() {
        val case = Validation.validateText("2010guabo@gmail.com", 3)
        assertEquals("", case)
    }

    @Test
    fun validateNumber_test() {
        val case = Validation.validateText("123456", 4)
        assertEquals("", case)
    }

    @Test
    fun validatePassword_test() {
        val case = Validation.validateText("Yjot1997", 5)
        assertEquals("", case)
    }
}