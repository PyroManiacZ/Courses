package ru.kechkinnd.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import ru.kechkinnd.core.network.api.CourseApi

val networkModule = module {
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory(contentType)
            )
            .build()
    }
    single { get<Retrofit>().create(CourseApi::class.java) }
}
