package com.yjotdev.empprimaria.domain.repository

interface StringRepository {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg args: Any): String
}