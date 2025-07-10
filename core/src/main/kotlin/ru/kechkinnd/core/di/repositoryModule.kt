package ru.kechkinnd.core.di

import org.koin.dsl.module
import ru.kechkinnd.core.repository.CoursesRepository
import ru.kechkinnd.core.repository.FavoritesRepository

val repositoryModule = module {
    single { FavoritesRepository(get()) }
    single { CoursesRepository(get(), get()) }
}


