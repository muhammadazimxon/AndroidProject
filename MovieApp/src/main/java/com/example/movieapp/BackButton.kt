package com.example.movieapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BackButton(
    navController: NavController,
    route: String
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .zIndex(1f)
            .background(Color.Transparent)
            .offset(x = 10.dp, y = 35.dp)
            .clip(RoundedCornerShape(7.dp))
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(7.dp))
            .clickable(onClick = {
                navController.popBackStack(
                    route = route,
                    inclusive = false
                )
            }),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    BackButton(route = "Application", navController = rememberNavController())
}