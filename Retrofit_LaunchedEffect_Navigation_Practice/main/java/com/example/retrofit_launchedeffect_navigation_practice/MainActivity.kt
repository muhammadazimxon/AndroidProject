package com.example.retrofit_launchedeffect_navigation_practice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retrofit_launchedeffect_navigation_practice.models.Meme
import com.example.retrofit_launchedeffect_navigation_practice.screens.DetailScreen
import com.example.retrofit_launchedeffect_navigation_practice.screens.MainScreen
import com.example.retrofit_launchedeffect_navigation_practice.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var memesList by remember {
                mutableStateOf(listOf<Meme>())
            }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        RetrofitInstance.api.getMemesList()
                    } catch (e: IOException) {
                        Log.d("TAG", "IO error: ${e.message}")
                        Toast.makeText(
                            this@MainActivity,
                            "IO Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    } catch (e: HttpException) {
                        Toast.makeText(
                            this@MainActivity,
                            "HTTP Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            memesList = response.body()!!.data.memes
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = "MainScreen"
            ) {
                composable(route = "MainScreen") { backStack ->
                    MainScreen(
                        memesList = memesList,
                        navController = navController
                    )
                }
                composable(route = "DetailScreen") { backStack ->
                    DetailScreen()
                }
            }
        }
    }
}