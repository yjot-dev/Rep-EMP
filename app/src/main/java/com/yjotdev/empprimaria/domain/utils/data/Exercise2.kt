package com.yjotdev.empprimaria.domain.utils.data

import com.yjotdev.empprimaria.domain.entity.Exercise2Entity
import com.yjotdev.empprimaria.R

/**
 * Elegir opcion correcta
 **/
object Exercise2 {
    val data = listOf(
        Exercise2Entity(
            question = R.string.exercise2_question1,
            answer = listOf(
                Pair(R.string.exercise2_answer1, false),
                Pair(R.string.exercise2_answer2, false),
                Pair(R.string.exercise2_answer3, false),
                Pair(R.string.exercise2_answer4, true)
            )
        ),
        Exercise2Entity(
            question = R.string.exercise2_question2,
            answer = listOf(
                Pair(R.string.exercise2_answer1, true),
                Pair(R.string.exercise2_answer2, false),
                Pair(R.string.exercise2_answer3, false),
                Pair(R.string.exercise2_answer4, false)
            )
        ),
        Exercise2Entity(
            question = R.string.exercise2_question3,
            answer = listOf(
                Pair(R.string.exercise2_answer1, false),
                Pair(R.string.exercise2_answer2, true),
                Pair(R.string.exercise2_answer3, false),
                Pair(R.string.exercise2_answer4, false)
            )
        ),
        Exercise2Entity(
            question = R.string.exercise2_question4,
            answer = listOf(
                Pair(R.string.exercise2_answer1, false),
                Pair(R.string.exercise2_answer2, false),
                Pair(R.string.exercise2_answer3, true),
                Pair(R.string.exercise2_answer4, false)
            )
        )
    )
}