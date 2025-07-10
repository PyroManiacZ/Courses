package ru.kechkinnd.features.favorites.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.kechkinnd.core.database.CourseEntity
import ru.kechkinnd.core.network.model.CourseDto
import ru.kechkinnd.core.repository.CoursesRepository
import ru.kechkinnd.core.repository.FavoritesRepository

data class FavoritesUiState(
    val courses: List<CourseDto> = emptyList(),
    val isLoading: Boolean = true
)

class FavoritesViewModel(
    private val coursesRepo: CoursesRepository,
    private val favRepo: FavoritesRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = combine(
        coursesRepo.coursesFlow,
        favRepo.getFavorites()
    ) { courses, favorites ->
        val favIds = favorites.map { it.id }.toSet()
        val filtered = courses.filter { it.id in favIds }
        FavoritesUiState(courses = filtered, isLoading = false)
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, FavoritesUiState())

    fun toggleFavorite(course: CourseDto) = viewModelScope.launch {
        if (course.hasLike) favRepo.remove(course)
        else favRepo.add(course)
    }
}

fun CourseDto.toEntity() = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    publishDate = publishDate,
    hasLike = hasLike
)
