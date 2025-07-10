package ru.kechkinnd.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    @SerialName("courses") val courses: List<CourseDto>
)