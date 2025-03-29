package com.yjotdev.empprimaria.application.mvvm.model

data class StoryModel(
    val paragraph: String = "",
    val question: String = "",
    val answer: List<Pair<String, Boolean>> = listOf(),
)