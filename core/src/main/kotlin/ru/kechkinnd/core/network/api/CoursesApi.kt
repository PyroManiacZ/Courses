package ru.kechkinnd.core.network.api

import retrofit2.http.GET
import ru.kechkinnd.core.network.model.CoursesResponse

interface CourseApi {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}
