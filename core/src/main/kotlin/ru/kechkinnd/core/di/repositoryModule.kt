package ru.kechkinnd.core.di

import org.koin.dsl.module
import ru.kechkinnd.core.database.CourseDao
import ru.kechkinnd.core.network.api.CourseApi
import ru.kechkinnd.core.repository.CoursesRepository
import ru.kechkinnd.core.repository.FavoritesRepository

val repositoryModule = module {
    single { get<CourseApi>() }
    single { get<CourseDao>() }
    single { FavoritesRepository(get()) }
    single { CoursesRepository(get(), get()) }
}
