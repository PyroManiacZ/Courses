package ru.kechkinnd.features.courses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.kechkinnd.core.repository.CoursesRepository
import ru.kechkinnd.core.network.model.CourseDto

data class CoursesUiState(
    val isLoading: Boolean = false,
    val courses: List<CourseDto> = emptyList(),
    val error: String? = null
)

class CoursesViewModel(private val repo: CoursesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CoursesUiState(isLoading = true))
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { repo.fetchCourses() }
                .onSuccess { list ->
                    _uiState.value = CoursesUiState(courses = list)
                }
                .onFailure { e ->
                    _uiState.value = CoursesUiState(error = e.message)
                }
        }
    }
}
