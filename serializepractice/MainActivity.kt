package com.example.serializepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun ScreenA(
    onNavigate: (Int) -> Unit,
    counter: Int
) {
    var temp by remember { mutableIntStateOf(counter) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Screen A",
            fontSize = 25.sp
        )
        ElevatedButton(
            onClick = { onNavigate(temp) }
        ) {
            Text(
                text = "Screen B"
            )
        }
        Button(onClick = { temp += 1 }) {
            Text(text = "Click me")
        }
        Text(text = "$temp")
    }
}

@Composable
fun ScreenB(
    onNavigate: (Int) -> Unit,
    counter: Int
) {
    var temp by remember { mutableIntStateOf(counter) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Screen B",
            fontSize = 25.sp
        )
        ElevatedButton(
            onClick = { onNavigate(temp) }
        ) {
            Text(
                text = "Screen A"
            )
        }
        Button(onClick = { temp += 1 }) {
            Text(text = "Click me")
        }
        Text(text = "$temp")
    }
}

@Serializable
data class ScreenA(val counter: Int = 0)

@Serializable
data class ScreenB(val counter: Int)

@Preview(showBackground = true)
@Composable
fun Preview() {
    val navController = rememberNavController()
    var counter by remember { mutableIntStateOf(0) }
    NavHost(
        navController = navController,
        startDestination = ScreenA
    ) {
        composable<ScreenA> { backStackEntry ->
            val nav = backStackEntry.toRoute<ScreenB>()
            ScreenA(
                onNavigate = { value: Int ->
                    counter = value
                    navController.navigate(ScreenB(nav.counter)) {
                        popUpTo(ScreenA(nav.counter)) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                counter = counter
            )
        }
        composable<ScreenB> { backStackEntry ->
            val nav = backStackEntry.toRoute<ScreenA>()
            ScreenB(
                onNavigate = { value: Int ->
                    counter = value
                    navController.navigate(ScreenA(nav.counter)) {
                        popUpTo(ScreenB(nav.counter)) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                counter = counter
            )
        }
    }
}