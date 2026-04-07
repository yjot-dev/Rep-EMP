package com.yjotdev.empprimaria.domain.utils.data

import com.yjotdev.empprimaria.domain.entity.StoryEntity
import com.yjotdev.empprimaria.R

object Stories {
    val data = listOf(
        listOf(
            StoryEntity(
                paragraph = R.string.story_paragraph1,
                question = R.string.story_question1,
                answer = listOf(
                    Pair("1970", false),
                    Pair("1980", false),
                    Pair("1976", true),
                    Pair("1978", false)
                )
            ),
            StoryEntity(
                paragraph = R.string.story_paragraph2,
                question = R.string.story_question2,
                answer = listOf(
                    Pair("Iphone", true),
                    Pair("Gemini", false),
                    Pair("Kotlin", false),
                    Pair("Chat-GPT", false)
                )
            ),
            StoryEntity(
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