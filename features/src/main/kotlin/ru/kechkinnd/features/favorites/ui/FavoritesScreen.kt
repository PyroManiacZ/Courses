package ru.kechkinnd.features.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.kechkinnd.core.database.CourseEntity
import ru.kechkinnd.features.favorites.data.FavoritesViewModel
import ru.kechkinnd.features.favorites.data.toEntity

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Избранное",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                }

                state.courses.isEmpty() -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Избранных курсов нет", color = MaterialTheme.colorScheme.onBackground)
                }

                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.courses) { course ->
                        FavoriteItem(
                            course = course.toEntity(),
                            onRemove = { viewModel.toggleFavorite(course) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    course: CourseEntity,
    onRemove: () -> Unit
) {
    val lightGrayBackground = Color(0xFFB0B0B0)
    val whiteTextColor = Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = lightGrayBackground),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = whiteTextColor
                )
                IconButton(onClick = onRemove) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = whiteTextColor
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                course.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = whiteTextColor
            )
        }
    }
}
