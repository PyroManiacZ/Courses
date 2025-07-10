package ru.kechkinnd.features.courses.di

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import ru.kechkinnd.features.courses.ui.CoursesViewModel

val coursesModule = module {
    viewModel { CoursesViewModel(get()) }
}