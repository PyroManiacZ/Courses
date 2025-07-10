package ru.kechkinnd.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.kechkinnd.core.network.api.CourseApi

val networkModule = module {
    single {
        Json { ignoreUnknownKeys = true }
    }

    single {
        val contentType = "application/json".toMediaType()
        val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor().apply {
            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .client(okHttpClient) // важно добавить клиент с логированием
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .build()
    }

    single<CourseApi> { get<Retrofit>().create(CourseApi::class.java) }
}

