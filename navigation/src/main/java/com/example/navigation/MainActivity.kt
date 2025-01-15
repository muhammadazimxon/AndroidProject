package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}
@Composable
fun Home(
    onHome: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Home",
                fontSize = 30.sp,
                fontWeight = FontWeight.W400,
                color = Color.Blue
            )
        }
        Text(
            text = "Home",
            fontSize = 45.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green)
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onHome
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = Color.Red
                    )
                }
                Text(
                    text = "Home",
                    color = Color.Red
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Search",
                    color = Color.Blue
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSettings
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Settings",
                    color = Color.Blue
                )
            }
        }
    }
}
@Composable
fun Search(
    onHome: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Search",
                fontSize = 30.sp,
                fontWeight = FontWeight.W400,
                color = Color.Blue
            )
        }
        Text(
            text = "Search",
            fontSize = 45.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green)
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onHome
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Home",
                    color = Color.Blue
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color.Red
                    )
                }
                Text(
                    text = "Search",
                    color = Color.Red
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSettings
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Settings",
                    color = Color.Blue
                )
            }
        }
    }
}
@Composable
fun Settings(
    onHome: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Settings",
                fontSize = 30.sp,
                fontWeight = FontWeight.W400,
                color = Color.Blue
            )
        }
        Text(
            text = "Settings",
            fontSize = 45.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .background(Color.Green)
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onHome
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Home",
                    color = Color.Blue
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color.Blue
                    )
                }
                Text(
                    text = "Search",
                    color = Color.Blue
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onSettings
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings",
                        tint = Color.Red
                    )
                }
                Text(
                    text = "Settings",
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(route = "Home") {
            Home(
                onHome = {
                    navController.navigate("Home") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Home") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSearch = {
                    navController.navigate("Search") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Search") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSettings = {
                    navController.navigate("Settings") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Settings") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = "Search") {
            Search(
                onHome = {
                    navController.navigate("Home") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Home") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSearch = {
                    navController.navigate("Search") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Search") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSettings = {
                    navController.navigate("Settings") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Settings") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = "Settings") {
            Settings(
                onHome = {
                    navController.navigate("Home") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Home") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSearch = {
                    navController.navigate("Search") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Search") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSettings = {
                    navController.navigate("Settings") {
                        popUpTo(route = navController.graph.startDestinationRoute ?: "Settings") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}