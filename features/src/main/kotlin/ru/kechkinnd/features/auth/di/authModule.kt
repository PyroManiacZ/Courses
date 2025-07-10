package ru.kechkinnd.features.auth.di

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import ru.kechkinnd.features.auth.data.AuthViewModel

val authModule = module {
    viewModel { AuthViewModel() }
}
