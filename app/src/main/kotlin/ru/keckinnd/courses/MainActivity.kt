package ru.keckinnd.courses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kechkinnd.features.auth.ui.AuthScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "ru/kechkinnd/features/auth"
                    ) {
                        composable("ru/kechkinnd/features/auth") {
                            AuthScreen(
                                onLoginSuccess = {
                                    // позже будем навигировать на главный экран
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

