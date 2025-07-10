package ru.kechkinnd.core.repository

import ru.kechkinnd.core.network.api.CourseApi
import ru.kechkinnd.core.network.model.CourseDto

class CoursesRepository(private val api: CourseApi) {
    suspend fun getCourses(): List<CourseDto> = api.getCourses().courses
}
