package ru.kechkinnd.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val publishDate: String,
    val hasLike: Boolean
)