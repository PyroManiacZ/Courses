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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.kechkinnd.core.database.CourseEntity

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        state.courses.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Избранных курсов нет")
        }

        else -> LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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


@Composable
fun FavoriteItem(
    course: CourseEntity,
    onRemove: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(course.title, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                course.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
