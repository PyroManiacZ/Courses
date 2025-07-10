package ru.kechkinnd.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.kechkinnd.core.database.AppDatabase
import ru.kechkinnd.core.database.CourseDao

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "courses-db"
        )
            .fallbackToDestructiveMigration() // для дева
            .build()
    }
    single<CourseDao> { get<AppDatabase>().courseDao() }
}