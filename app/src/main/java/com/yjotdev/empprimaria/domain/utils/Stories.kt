package com.yjotdev.empprimaria.domain.utils

import com.yjotdev.empprimaria.domain.model.StoryModel
import com.yjotdev.empprimaria.R

object Stories {
    val data = listOf(
        listOf(
            StoryModel(
                paragraph = R.string.story_paragraph1,
                question = R.string.story_question1,
                answer = listOf(
                    Pair("1970", false),
                    Pair("1980", false),
                    Pair("1976", true),
                    Pair("1978", false)
                )
            ),
            StoryModel(
                paragraph = R.string.story_paragraph2,
                question = R.string.story_question2,
                answer = listOf(
                    Pair("Iphone", true),
                    Pair("Gemini", false),
                    Pair("Kotlin", false),
                    Pair("Chat-GPT", false)
                )
            ),
            StoryModel(
                paragraph = R.string.story_paragraph3,
                question = R.string.story_question3,
                answer = listOf(
                    Pair("Apple", false),
                    Pair("Microsoft", false),
                    Pair("Amazon", false),
                    Pair("Pixar", true)
                )
            )
        )
    )
}