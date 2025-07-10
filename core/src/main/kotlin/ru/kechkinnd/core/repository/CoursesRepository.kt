package ru.kechkinnd.core.repository

import ru.kechkinnd.core.network.api.CoursesApi
import ru.kechkinnd.core.network.model.CourseDto

class CoursesRepository(private val api: CoursesApi) {
    suspend fun fetchCourses(): List<CourseDto> = api.getCourses()
}
