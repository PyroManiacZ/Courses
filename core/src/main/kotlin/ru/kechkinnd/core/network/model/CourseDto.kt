package ru.kechkinnd.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("text") val text: String,
    @SerialName("price") val price: String,
    @SerialName("rate") val rate: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("hasLike") val hasLike: Boolean,
    @SerialName("publishDate") val publishDate: String
)