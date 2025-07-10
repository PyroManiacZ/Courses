package ru.kechkinnd.features.courses.data

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.kechkinnd.core.network.model.CourseDto
import ru.kechkinnd.features.courses.ui.CoursesViewModel

@Composable
fun CoursesScreen(viewModel: CoursesViewModel) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.courses) {
        Log.d("CoursesScreen", "Loaded courses: ${state.courses.size}")
    }

    when {
        state.isLoading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Ошибка: ${state.error}")
            }
        }
        state.courses.isEmpty() -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Список пуст!")
            }
        }
        else -> {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.courses) { course ->
                    CourseItem(
                        course = course,
                        onLikeClick = { viewModel.toggleFavorite(course) }
                    )
                }
            }
        }
    }
}

@Composable
fun CourseItem(
    course: CourseDto,
    onLikeClick: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box {
            Column(Modifier.padding(16.dp)) {
                Text(course.title, style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(4.dp))
                Text(
                    text = course.text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Цена: ${course.price}", style = MaterialTheme.typography.bodySmall)
                    Text("Рейтинг: ${course.rate}", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.height(4.dp))
                Text("Старт: ${course.startDate}", style = MaterialTheme.typography.bodySmall)
                Text("Опубликовано: ${course.publishDate}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(
                onClick = onLikeClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (course.hasLike)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,
                    contentDescription = if (course.hasLike)
                        "Удалить из избранного"
                    else
                        "В избранное",
                    tint = if (course.hasLike) Color.Red else Color.Gray
                )
            }
        }
    }
}
