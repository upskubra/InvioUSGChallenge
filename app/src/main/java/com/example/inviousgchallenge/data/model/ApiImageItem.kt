package com.example.inviousgchallenge.data.model


data class ApiImageItem(
    val breedId: Int = 0,
    val id: String?,
    val image: ApiImage?,
    val name: String?,
    val temperament: String?,
    val wikipedia_url: String?,
    val energy_level: Int?,
    val description: String?,
    val origin: String?
)