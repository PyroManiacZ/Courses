package ru.kechkinnd.features.courses.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.kechkinnd.core.network.model.CourseDto
import ru.kechkinnd.features.courses.ui.CoursesViewModel

@Composable
fun CoursesScreen(viewModel: CoursesViewModel) {
    val state by viewModel.uiState.collectAsState()

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ошибка загрузки: ${state.error}")
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { viewModel.reloadCourses() }) {
                        Text("Повторить")
                    }
                }
            }
        }
        state.courses.isEmpty() -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Список курсов пуст")
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
        Box {
            Column(Modifier.padding(16.dp)) {
                Text(
                    course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = whiteTextColor
                )

                Spacer(Modifier.height(4.dp))
                Text(
                    text = course.text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = whiteTextColor
                )

                Spacer(Modifier.height(8.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Цена: ${course.price}", style = MaterialTheme.typography.bodySmall, color = whiteTextColor)
                    Text("Рейтинг: ${course.rate}", style = MaterialTheme.typography.bodySmall, color = whiteTextColor)
                }
                Spacer(Modifier.height(4.dp))
                Text("Старт: ${course.startDate}", style = MaterialTheme.typography.bodySmall, color = whiteTextColor)
                Text("Опубликовано: ${course.publishDate}", style = MaterialTheme.typography.bodySmall, color = whiteTextColor)
            }

            IconButton(
                onClick = onLikeClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (course.hasLike)
                        Icons.Filled.Bookmark
                    else
                        Icons.Outlined.BookmarkBorder,
                    contentDescription = if (course.hasLike)
                        "Удалить из избранного"
                    else
                        "В избранное",
                    tint = if (course.hasLike) Color.Green else whiteTextColor
                )
            }
        }
    }
}