package com.example.musicplayerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayerapp.scaffold.ScaffoldSidePreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MenuPreview()
        }
    }
}

@Composable
fun MenuScreen() {
    ScaffoldSidePreview()
}

@Preview
@Composable
fun MenuPreview() {
    MenuScreen()
}