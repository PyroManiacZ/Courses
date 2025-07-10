package ru.kechkinnd.courses

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kechkinnd.core.di.networkModule
import ru.kechkinnd.core.di.repositoryModule
import ru.kechkinnd.features.auth.di.authModule
import ru.kechkinnd.features.courses.di.coursesModule

class CoursesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoursesApp)
            modules(
                authModule,
                networkModule,
                repositoryModule,
                coursesModule
                // , другие модули…
            )
        }
    }
}
