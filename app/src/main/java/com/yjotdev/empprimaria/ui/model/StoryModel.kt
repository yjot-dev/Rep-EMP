package com.yjotdev.empprimaria.ui.model

data class StoryModel(
    val paragraph: String = "",
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf(),
)