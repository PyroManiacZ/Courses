package ru.kechkinnd.features.courses.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import ru.kechkinnd.features.courses.data.AccountScreen
import ru.kechkinnd.features.courses.data.CoursesScreen
import ru.kechkinnd.features.courses.data.FavoritesScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Определяем табы: route + иконка + лейбл
    val tabs = listOf(
        TabItem("courses", Icons.Default.Home, "Курсы"),
        TabItem("favorites", Icons.Default.Favorite, "Избранное"),
        TabItem("account", Icons.Default.Person, "Профиль")
    )
    var selectedTab by remember { mutableStateOf("courses") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = selectedTab == tab.route,
                        onClick = {
                            selectedTab = tab.route
                            navController.navigate(tab.route) {
                                // Сохраняем состояние и избегаем дублирования
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "courses",
            modifier = Modifier.padding(padding)
        ) {
            composable("courses") { CoursesScreen() }
            composable("favorites") { FavoritesScreen() }
            composable("account") { AccountScreen() }
        }
    }
}

// Модель для таба
data class TabItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)
