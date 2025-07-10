package ru.kechkinnd.features.courses.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kechkinnd.features.courses.ui.CoursesViewModel
import ru.kechkinnd.features.favorites.ui.FavoritesViewModel

val featureModule = module {
    // ViewModel для экрана курсов
    viewModel { CoursesViewModel(get(), get()) }
    // ViewModel для экрана избранного
    viewModel { FavoritesViewModel(get(), get()) }
}
