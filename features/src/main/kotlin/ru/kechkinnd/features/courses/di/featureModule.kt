package ru.kechkinnd.features.courses.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kechkinnd.features.courses.ui.CoursesViewModel
import ru.kechkinnd.features.favorites.data.FavoritesViewModel

val featureModule = module {
    viewModelOf(::CoursesViewModel)
    viewModelOf(::FavoritesViewModel)
}