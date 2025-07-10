package ru.kechkinnd.core.repository

import kotlinx.coroutines.flow.Flow
import ru.kechkinnd.core.database.CourseDao
import ru.kechkinnd.core.database.CourseEntity
import ru.kechkinnd.core.network.model.CourseDto

class FavoritesRepository(private val dao: CourseDao) {
    fun getFavorites(): Flow<List<CourseEntity>> = dao.getAll()

    suspend fun add(course: CourseDto) {
        dao.insert(course.toEntity())
    }

    suspend fun remove(course: CourseDto) {
        dao.delete(course.toEntity())
    }

    suspend fun isFavorite(id: Int): Boolean = dao.exists(id)
}

// Расширение для маппинга
private fun CourseDto.toEntity() = CourseEntity(
    id, title, text, price, rate, startDate, publishDate, hasLike
)
