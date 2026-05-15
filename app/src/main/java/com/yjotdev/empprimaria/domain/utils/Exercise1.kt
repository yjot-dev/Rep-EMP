package com.yjotdev.empprimaria.domain.utils

import com.yjotdev.empprimaria.domain.model.Exercise1Model
import com.yjotdev.empprimaria.R

/**
 * Verdadero o falso
 **/
object Exercise1 {
    val data = listOf(
        Exercise1Model(
            question = R.string.exercise1_question1,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, true),
                Pair(R.string.exercise1_answer_false, false)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question2,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, false),
                Pair(R.string.exercise1_answer_false, true)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question3,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, true),
                Pair(R.string.exercise1_answer_false, false)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question4,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, false),
                Pair(R.string.exercise1_answer_false, true)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question5,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, true),
                Pair(R.string.exercise1_answer_false, false)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question6,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, false),
                Pair(R.string.exercise1_answer_false, true)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question7,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, true),
                Pair(R.string.exercise1_answer_false, false)
            )
        ),
        Exercise1Model(
            question = R.string.exercise1_question8,
            answer = listOf(
                Pair(R.string.exercise1_answer_true, false),
                Pair(R.string.exercise1_answer_false, true)
            )
        )
    )
}