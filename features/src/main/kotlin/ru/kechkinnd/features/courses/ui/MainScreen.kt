package ru.kechkinnd.features.courses.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import org.koin.androidx.compose.koinViewModel
import ru.kechkinnd.features.courses.data.AccountScreen
import ru.kechkinnd.features.courses.data.CoursesScreen
import ru.kechkinnd.features.favorites.ui.FavoritesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: CoursesViewModel = koinViewModel()
    val query by viewModel.searchQuery.collectAsState()
    val sortKey by viewModel.sortKey.collectAsState()

    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf("courses") }
    var showMenu by remember { mutableStateOf(false) }

    val tabs = listOf(
        TabItem("courses", Icons.Outlined.Home, "Главная"),
        TabItem("favorites", Icons.Outlined.BookmarkBorder, "Избранное"),
        TabItem("account", Icons.Outlined.Person, "Аккаунт")
    )

    Scaffold(
        containerColor = Color(0xFF2C2C2C),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = viewModel::onSearchChange,
                        placeholder = { Text("Поиск курса", color = Color.Gray) },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray)
                        },
                        colors = TextFieldDefaults.colors(
                            // цвета текста
                            focusedTextColor       = Color.White,
                            unfocusedTextColor     = Color.White,
                            disabledTextColor      = Color.Gray,
                            // фон контейнера
                            focusedContainerColor   = Color(0xFF2C2C2C),
                            unfocusedContainerColor = Color(0xFF2C2C2C),
                            disabledContainerColor  = Color(0xFF2C2C2C),
                            // курсор
                            cursorColor            = Color(0xFF4CAF50),

                            focusedIndicatorColor   = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor  = Color.Transparent,
                            // иконка и плейсхолдер
                            focusedLeadingIconColor   = Color.Gray,
                            unfocusedLeadingIconColor = Color.Gray,
                            disabledLeadingIconColor  = Color.Gray,
                            focusedPlaceholderColor   = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            disabledPlaceholderColor  = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                },
                actions = {
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
                                        Icon(Icons.Filled.Check, contentDescription = null)
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
                                        Icon(Icons.Filled.Check, contentDescription = null)
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
                                        Icon(Icons.Filled.Check, contentDescription = null)
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = Color.White, thickness = 1.dp)
                NavigationBar(
                    containerColor = Color.Black,
                    tonalElevation = 0.dp
                ) {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label,
                                    tint = if (selectedTab == tab.route) Color(0xFF4CAF50) else Color.White
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    color = if (selectedTab == tab.route) Color(0xFF4CAF50) else Color.White
                                )
                            },
                            selected = selectedTab == tab.route,
                            onClick = {
                                selectedTab = tab.route
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color(0xFF2C2C2C),
                                selectedIconColor = Color(0xFF4CAF50),
                                selectedTextColor = Color(0xFF4CAF50),
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "courses",
            modifier = Modifier.padding(padding)
        ) {
            composable("courses") {
                CoursesScreen(viewModel = viewModel)
            }
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
