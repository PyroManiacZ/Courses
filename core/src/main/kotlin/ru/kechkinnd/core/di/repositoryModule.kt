package ru.kechkinnd.core.di

import org.koin.dsl.module
import ru.kechkinnd.core.repository.CoursesRepository

val repositoryModule = module {
    single { CoursesRepository(get()) }
}
