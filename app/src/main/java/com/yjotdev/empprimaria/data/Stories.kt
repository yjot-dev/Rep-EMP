package com.yjotdev.empprimaria.data

import com.yjotdev.empprimaria.ui.model.StoryModel

object Stories {
    val data = listOf(
        listOf(
            StoryModel(
                paragraph = "Steve Jobs fue un emprendedor visionario y carismático " +
                        "que dejó una huella indeleble en la industria tecnológica. " +
                        "Junto con Steve Wozniak y Ronald Wayne, Jobs fundó Apple Computer, " +
                        "Inc. en 1976 en el garaje de su casa.",
                question = "¿En qué año Steve Jobs fundó Apple Computer, Inc.?",
                answer = listOf(
                    Pair("1970", false),
                    Pair("1980", false),
                    Pair("1976", true),
                    Pair("1978", false)
                )
            ),
            StoryModel(
                paragraph = "Jobs jugó un papel crucial en el desarrollo de productos " +
                    "icónicos como el Apple I, Apple II, iMac, iPod, iPhone y iPad. " +
                    "Estos productos revolucionaron sus respectivos mercados y cambiaron " +
                    "la forma en que interactuamos con la tecnología.",
                question = "¿Cuál de los siguientes productos son icónicos de Apple Computer, Inc.?",
                answer = listOf(
                    Pair("Iphone", true),
                    Pair("Gemini", false),
                    Pair("Kotlin", false),
                    Pair("Chat-GPT", false)
                )
            ),
            StoryModel(
                paragraph = "Además de Apple, Jobs también adquirió Pixar en 1986, que se convirtió " +
                        "en una exitosa compañía de animación, produciendo películas como \"Toy Story\" y" +
                        " \"Buscando a Nemo\".",
                question = "¿Cual fue el nombre de la empresa que adquirió Steve Jobs en 1986?",
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