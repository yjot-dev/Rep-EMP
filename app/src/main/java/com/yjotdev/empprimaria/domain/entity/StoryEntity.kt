package com.yjotdev.empprimaria.domain.entity

data class StoryEntity(
    val paragraph: String = "",
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf(),
)