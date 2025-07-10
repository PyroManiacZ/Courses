package ru.kechkinnd.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.kechkinnd.core.network.api.CoursesApi

val networkModule = module {
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://your.api.mock/") // замените на ваш базовый URL
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
    }
    single { get<Retrofit>().create(CoursesApi::class.java) }
}