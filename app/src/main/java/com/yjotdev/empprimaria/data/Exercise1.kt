package com.yjotdev.empprimaria.data

import com.yjotdev.empprimaria.ui.model.Exercise1Model

/**
 * Verdadero o falso
 **/
object Exercise1 {
    val data = listOf(
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "El ingreso es la cantidad de bienes obtenidos por el " +
                    "precio de venta de un producto o servicio.",
            answer = listOf(
                Pair("Verdadero", true),
                Pair("Falso", false)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "El costo es la cantidad de bienes obtenidos por el " +
                    "precio de venta de un producto o servicio.",
            answer = listOf(
                Pair("Verdadero", false),
                Pair("Falso", true)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "Los costos son todos los desembolsos que hace la empresa para producir un " +
                    "bien o servicio que generará un beneficio económico, dentro de un ejercicio " +
                    "contable.",
            answer = listOf(
                Pair("Verdadero", true),
                Pair("Falso", false)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "Los gastos son todos los desembolsos que hace la empresa para producir un " +
                    "bien o servicio que generará un beneficio económico, dentro de un ejercicio " +
                    "contable.",
            answer = listOf(
                Pair("Verdadero", false),
                Pair("Falso", true)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "Un gasto es el desembolso que debe hacer el emprendimiento para poder " +
                    "llevar a cabo sus actividades, y que no se relacionen directamente con la fabri" +
                    "cación o compra de productos.",
            answer = listOf(
                Pair("Verdadero", true),
                Pair("Falso", false)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "Una inversión es el desembolso que debe hacer el emprendimiento para poder " +
                    "llevar a cabo sus actividades, y que no se relacionen directamente con la fabri" +
                    "cación o compra de productos.",
            answer = listOf(
                Pair("Verdadero", false),
                Pair("Falso", true)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "La inversión consiste en asignar un capital a una actividad que genere un em" +
                    "prendimiento y permita alcanzar los objetivos propuestos.",
            answer = listOf(
                Pair("Verdadero", true),
                Pair("Falso", false)
            )
        ),
        Exercise1Model(
            question = "Elija verdadero o falso \n" +
                    "El ingreso consiste en asignar un capital a una actividad que genere un em" +
                    "prendimiento y permita alcanzar los objetivos propuestos.",
            answer = listOf(
                Pair("Verdadero", false),
                Pair("Falso", true)
            )
        ),
    )
}