package com.example.musicplayerapp.scaffold

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.musicplayerapp.scaffold.player.Player
import com.example.musicplayerapp.scaffold.playerList.PlayerList
import com.example.musicplayerapp.viewModel.MusicViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicViewModel.ScaffoldSide(
    onPlayer: (Int) -> Unit,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                ListItem(
                    headlineContent = {}
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .shadow(5.dp)
                        .border(1.dp, Color.DarkGray),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "menu",
                                tint = Color.Black
                            )
                        }
                    },
                    title = { Text(text = "Music") }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .systemBarsPadding()
                        .shadow(
                            elevation = 15.dp
                        )
                        .border(1.dp, Color.DarkGray)
                        .clickable(onClick = { onPlayer(selectedId) }),
                    windowInsets = WindowInsets(12.dp, 15.dp, 12.dp, 15.dp),
                    contentColor = Color.DarkGray,
                    containerColor = Color.White
                ) {
                    BottomBarPlayer(
                        music = musics.find { it.id == selectedId }
                            ?: wrongMusic,
                        viewModel = this@ScaffoldSide,
                        context = context,
                        isPlaying = mediaIsPlaying
                    )
                }
            }
        ) { contentPadding ->
            PlayerList(contentPadding = contentPadding)
        }
    }
}

@Serializable
data class Player(val id: Int)

@Serializable
object ScaffoldSide

@Preview(showBackground = true)
@Composable
fun ScaffoldSidePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel = MusicViewModel()
    NavHost(
        navController = navController,
        startDestination = ScaffoldSide
    ) {
        composable<ScaffoldSide> {
            viewModel.ScaffoldSide(
                onPlayer = {
                    navController.navigate(Player(it)) {
                        launchSingleTop = true
                    }
                },
                context = context
            )
        }
        composable<Player> { backStackEntry ->
            val id = backStackEntry.toRoute<Player>().id
            viewModel.Player(
                id = id,
                onBack = {
                    navController.popBackStack()
                },
                context = context
            )
        }
    }
}