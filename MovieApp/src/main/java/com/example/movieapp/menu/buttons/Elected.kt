package com.example.movieapp.menu.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.BackButton
import com.example.movieapp.Movie
import com.example.movieapp.moviesDisplayer.MovieDisplayer

@Composable
fun Elected(
    navController: NavController,
    movies: List<Movie>,
    darkMode: Boolean
) {
    BackButton(navController = navController, route = "Application")
    Column(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color(26, 26, 26, 255) else Color.Transparent)
    ) {
        MovieDisplayer(movies = movies, navController = navController, darkMode = darkMode)
    }
}

@Preview(showBackground = true)
@Composable
fun ElectedPreview() {
    Elected(navController = rememberNavController(), movies = emptyList(), false)
}