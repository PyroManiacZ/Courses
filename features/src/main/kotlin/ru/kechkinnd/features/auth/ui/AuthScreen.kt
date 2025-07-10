package ru.kechkinnd.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.kechkinnd.features.auth.data.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    onAuthSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    var navigate by remember { mutableStateOf(false) }

    if (navigate) {
        LaunchedEffect(Unit) { onAuthSuccess() }
    }

    val darkBackground = Color(0xFF121212)
    val grayFieldBackground = Color(0xFF2C2C2C)
    val grayPlaceholder = Color(0xFF888888)
    val greenColor = Color(0xFF4CAF50)
    val blueVK = Color(0xFF3b5998)
    val orangeGoogle = Color(0xFFF57C00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Email label
        Text(text = "Email", color = Color.White, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            placeholder = { Text("example@gmail.com", color = grayPlaceholder) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(grayFieldBackground),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = grayFieldBackground,
                unfocusedContainerColor = grayFieldBackground,
                disabledContainerColor = grayFieldBackground,
                focusedIndicatorColor = greenColor,
                unfocusedIndicatorColor = grayPlaceholder,
                cursorColor = greenColor
            )
        )

        Text(text = "Пароль", color = Color.White, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = { Text("Введите пароль", color = grayPlaceholder) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(grayFieldBackground),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = grayFieldBackground,
                unfocusedContainerColor = grayFieldBackground,
                disabledContainerColor = grayFieldBackground,
                focusedIndicatorColor = greenColor,
                unfocusedIndicatorColor = grayPlaceholder,
                cursorColor = greenColor
            )
        )

        Button(
            onClick = {
                if (state.isLoginEnabled) {  // проверяем, что email и пароль валидны
                    viewModel.onLoginClick()
                    navigate = true
                }
            },
            enabled = state.isLoginEnabled,  // здесь включаем кнопку только при валидных email и пароле
            colors = ButtonDefaults.buttonColors(containerColor = greenColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Вход", color = Color.White, fontSize = 16.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { /* TODO: Регистрация */ }) {
                Text("Нету аккаунта? Регистрация", color = greenColor)
            }
            TextButton(onClick = { /* TODO: Забыл пароль */ }) {
                Text("Забыл пароль", color = greenColor)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { uriHandler.openUri("https://vk.com") },
                colors = ButtonDefaults.buttonColors(containerColor = blueVK),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("VK", color = Color.White)
            }

            Button(
                onClick = { uriHandler.openUri("https://accounts.google.com") },
                colors = ButtonDefaults.buttonColors(containerColor = orangeGoogle),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("OK", color = Color.White)
            }
        }
    }
}
