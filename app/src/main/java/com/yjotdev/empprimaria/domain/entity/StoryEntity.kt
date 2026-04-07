package com.yjotdev.empprimaria.domain.entity

data class StoryEntity(
    val paragraph: Int = 0,
    val question: Int = 0,
    val answer: List<Pair<String, Boolean>> = listOf(),
)