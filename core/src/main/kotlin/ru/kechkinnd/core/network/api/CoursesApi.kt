package ru.kechkinnd.core.network.api

import retrofit2.http.GET
import ru.kechkinnd.core.network.model.CourseDto

interface CoursesApi {
    @GET("courses") // для мокового эндпоинта, например /courses.json
    suspend fun getCourses(): List<CourseDto>
}