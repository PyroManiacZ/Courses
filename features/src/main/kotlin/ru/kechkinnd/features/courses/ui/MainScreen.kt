package ru.kechkinnd.features.courses.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material.icons.filled.*
import ru.kechkinnd.features.courses.data.AccountScreen
import ru.kechkinnd.features.courses.data.CoursesScreen
import ru.kechkinnd.features.favorites.ui.FavoritesScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: CoursesViewModel = koinViewModel()
    val query by viewModel.searchQuery.collectAsState()
    val sortKey by viewModel.sortKey.collectAsState()

    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf("courses") }

    val tabs = listOf(
        TabItem("courses", Icons.Default.Home, "Курсы"),
        TabItem("favorites", Icons.Default.Favorite, "Избранное"),
        TabItem("account", Icons.Default.Person, "Профиль")
    )
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = viewModel::onSearchChange,
                        placeholder = { Text("Поиск курса") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )
                },
                actions = {
                    // Кнопка сортировки
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Сортировка")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("По дате") },
                                onClick = {
                                    viewModel.onSortChange(CoursesViewModel.SortKey.DATE)
                                    showMenu = false
                                },
                                leadingIcon = {
                                    if (sortKey == CoursesViewModel.SortKey.DATE)
                                        Icon(Icons.Default.Check, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("По цене") },
                                onClick = {
                                    viewModel.onSortChange(CoursesViewModel.SortKey.PRICE)
                                    showMenu = false
                                },
                                leadingIcon = {
                                    if (sortKey == CoursesViewModel.SortKey.PRICE)
                                        Icon(Icons.Default.Check, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("По рейтингу") },
                                onClick = {
                                    viewModel.onSortChange(CoursesViewModel.SortKey.RATE)
                                    showMenu = false
                                },
                                leadingIcon = {
                                    if (sortKey == CoursesViewModel.SortKey.RATE)
                                        Icon(Icons.Default.Check, contentDescription = null)
                                }
                            )
                        }
                    }
                }
            )
        },
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
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
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
            composable("courses") { CoursesScreen(
                viewModel = viewModel
            ) }
            composable("favorites") {
                FavoritesScreen()
            }
            composable("account") {
                AccountScreen()
            }
        }
    }
}

// Модель для таба
data class TabItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)
