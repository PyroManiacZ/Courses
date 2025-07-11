package ru.kechkinnd.courses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import ru.kechkinnd.courses.ui.theme.CoursesTheme
import ru.kechkinnd.features.auth.ui.AuthScreen
import ru.kechkinnd.features.courses.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoursesTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "auth") {
                    composable("auth") {
                        AuthScreen(
                            onAuthSuccess = {
                                navController.navigate("main") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("main") {
                        MainScreen()
                    }
                }
            }
        }
    }
}
