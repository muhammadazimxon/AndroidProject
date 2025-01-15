package com.example.movieapp.menu.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.BackButton

@Composable
fun Setting(navController: NavController, darkMode: Boolean) {
    BackButton(
        navController = navController,
        route = "Application"
    )
    Column(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color(35, 26, 26, 255) else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Soon",
            fontWeight = FontWeight.W400,
            fontSize = 100.sp,
            color = Color(70, 125, 150, 255)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    Setting(
        navController = rememberNavController(),
        false
    )
}