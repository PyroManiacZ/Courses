package ru.kechkinnd.features.courses.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.kechkinnd.core.repository.CoursesRepository
import ru.kechkinnd.core.network.model.CourseDto
import ru.kechkinnd.core.repository.FavoritesRepository

data class CoursesUiState(
    val isLoading: Boolean = false,
    val courses: List<CourseDto> = emptyList(),
    val error: String? = null
)

class CoursesViewModel(
    private val coursesRepo: CoursesRepository,
    private val favRepo: FavoritesRepository
) : ViewModel() {


    // --- сортировка ---
    enum class SortKey { PRICE, RATE, DATE }

    private val _sortKey = MutableStateFlow(SortKey.DATE)
    val sortKey: StateFlow<SortKey> = _sortKey

    fun onSortChange(newKey: SortKey) {
        _sortKey.value = newKey
    }

    // --- поиск ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    // --- курсы и избранное ---
    private val coursesFlow: StateFlow<List<CourseDto>> = coursesRepo.coursesFlow

    private val favIdsFlow: Flow<Set<Int>> = favRepo.getFavorites()
        .map { list -> list.map { it.id }.toSet() }

    val uiState: StateFlow<CoursesUiState> = combine(
        coursesFlow,
        favIdsFlow,
        _searchQuery,
        _sortKey
    ) { courses, favIds, query, sortKey ->
        Log.d("CoursesViewModel", "Combining ${courses.size} courses, favorites: ${favIds.size}, query: '$query', sort: $sortKey")

        // 1. лайкнутые помечаем
        val updated = courses.map { dto ->
            dto.copy(hasLike = dto.id in favIds)
        }

        // 2. фильтрация по поиску
        val filtered = if (query.isBlank()) updated
        else updated.filter { it.title.contains(query, ignoreCase = true) }

        // 3. сортировка
        val sorted = when (sortKey) {
            SortKey.PRICE -> filtered.sortedBy {
                it.price.filter(Char::isDigit).toIntOrNull() ?: 0
            }
            SortKey.RATE -> filtered.sortedByDescending {
                it.rate.toDoubleOrNull() ?: 0.0
            }
            SortKey.DATE -> filtered.sortedBy { it.startDate }
        }

        CoursesUiState(courses = sorted)
    }
        .catch { e -> emit(CoursesUiState(error = e.localizedMessage)) }
        .stateIn(viewModelScope, SharingStarted.Lazily, CoursesUiState(isLoading = true))

    // --- загрузка курсов ---
    init {
        viewModelScope.launch {
            runCatching { coursesRepo.loadCourses() }
                .onFailure { e ->
                    // fallback на ошибку
                }
        }
    }

    fun reloadCourses() {
        viewModelScope.launch {
            runCatching { coursesRepo.loadCourses() }
                .onFailure { e ->
                    Log.e("CoursesViewModel", "Reload failed: ${e.localizedMessage}")
                }
        }
    }


    // --- переключение лайка ---
    fun toggleFavorite(course: CourseDto) = viewModelScope.launch {
        coursesRepo.toggleLike(course)
    }
}
