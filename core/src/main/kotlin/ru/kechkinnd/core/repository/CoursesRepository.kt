package ru.kechkinnd.core.repository

import android.util.Log
import kotlinx.coroutines.flow.*
import ru.kechkinnd.core.network.api.CourseApi
import ru.kechkinnd.core.network.model.CourseDto

class CoursesRepository(
    private val api: CourseApi,
    private val favoritesRepo: FavoritesRepository
) {
    private val _courses = MutableStateFlow<List<CourseDto>>(emptyList())
    val coursesFlow: StateFlow<List<CourseDto>> = _courses.asStateFlow()

    suspend fun loadCourses() {
        val response = api.getCourses()
        Log.d("CoursesRepository", "Response courses size: ${response.courses.size}")
        val favIds = favoritesRepo.getFavorites().first().map { it.id }.toSet()
        _courses.value = response.courses.map { it.copy(hasLike = it.id in favIds) }
    }

    suspend fun toggleLike(course: CourseDto) {
        _courses.update { list ->
            list.map {
                if (it.id == course.id) it.copy(hasLike = !it.hasLike) else it
            }
        }

        val newState = _courses.value.first { it.id == course.id }
        if (newState.hasLike) {
            favoritesRepo.add(newState)
        } else {
            favoritesRepo.remove(newState)
        }
    }
}

