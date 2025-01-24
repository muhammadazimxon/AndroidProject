package com.example.retrofit_launchedeffect_navigation_practice.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retrofit_launchedeffect_navigation_practice.models.Meme

@Composable
fun MainScreen(memesList: List<Meme>, navController: NavHostController) {

}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen(listOf<Meme>(), rememberNavController())
}