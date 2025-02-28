package com.yjotdev.empprimaria.data

import com.yjotdev.empprimaria.ui.model.Exercise2Model

/**
 * Elegir opcion correcta
 **/
object Exercise2 {
    val data = listOf(
        Exercise2Model(
            question = "Elija la respuesta correcta \n" +
                    "Es la cantidad de bienes obtenidos por el precio de venta de un " +
                    "producto o servicio.",
            answer = listOf(
                Pair("Costos", false),
                Pair("Gastos", false),
                Pair("Inversión", false),
                Pair("Ingresos", true)
            )
        ),
        Exercise2Model(
            question = "Elija la respuesta correcta \n" +
                    "Son todos los desembolsos que hace la empresa para producir un " +
                    "bien o servicio que generará un beneficio económico, dentro de un ejercicio " +
                    "contable.",
            answer = listOf(
                Pair("Costos", true),
                Pair("Gastos", false),
                Pair("Inversión", false),
                Pair("Ingresos", false)
            )
        ),
        Exercise2Model(
            question = "Elija la respuesta correcta \n" +
                    "Es el desembolso que debe hacer el emprendimiento para poder " +
                    "llevar a cabo sus actividades, y que no se relacionen directamente con la fabri" +
                    "cación o compra de productos.",
            answer = listOf(
                Pair("Costos", false),
                Pair("Gastos", true),
                Pair("Inversión", false),
                Pair("Ingresos", false)
            )
        ),
        Exercise2Model(
            question = "Elija la respuesta correcta \n" +
                    "Consiste en asignar un capital a una actividad que genere un em" +
                    "prendimiento y permita alcanzar los objetivos propuestos.",
            answer = listOf(
                Pair("Costos", false),
                Pair("Gastos", false),
                Pair("Inversión", true),
                Pair("Ingresos", false)
            )
        ),
    )
}