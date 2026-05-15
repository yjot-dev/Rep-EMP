package com.yjotdev.empprimaria.domain.model

data class StoryModel(
    val paragraph: Int = 0,
    val question: Int = 0,
    val answer: List<Pair<String, Boolean>> = listOf(),
)