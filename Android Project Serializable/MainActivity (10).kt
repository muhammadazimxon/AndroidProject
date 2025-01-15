package com.example.navigationpractice

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApplicationPreview()
        }
    }
}

data class Cell(
    val x: Dp,
    val y: Dp
)
val dataCells = listOf(
    Cell(10.dp, 10.dp),
    Cell(120.dp, 10.dp),
    Cell(230.dp, 10.dp),
    Cell(10.dp, 120.dp),
    Cell(120.dp, 120.dp),
    Cell(230.dp, 120.dp),
    Cell(10.dp, 230.dp),
    Cell(120.dp, 230.dp),
    Cell(230.dp, 230.dp),
)
val winWays = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6)
)
var darkMode by mutableStateOf(false)

data class Data(
    val dataText: String
)
fun isWin(dataCell: List<Data>, current: String): Boolean {
    return winWays.any { indices ->
        current == dataCell[indices[0]].dataText &&
                current == dataCell[indices[1]].dataText &&
                current == dataCell[indices[2]].dataText
    }
}
fun isDraw(cells:List<Data>) = cells.all { it.dataText.isNotBlank() }
fun getCurrentPlayer(isX: Boolean) = if(isX) "X" else "O"
@Composable
fun TicTacToe(
    comingStatusX: Int,
    comingStatusO: Int,
    comingStatusDraw: Int,
    onNavigateEnd: (Int, Int, Int) -> Unit
) {
    var cells by remember {
        mutableStateOf(listOf(
            Data(""), Data(""), Data(""),
            Data(""), Data(""), Data(""),
            Data(""), Data(""), Data("")
        ))
    }
    var currentPlayer by remember { mutableStateOf(true) }
    var isGameEnd by remember { mutableStateOf(false) }
    var statusDraw by remember { mutableIntStateOf(comingStatusDraw) }
    var statusX by remember { mutableIntStateOf(comingStatusX) }
    var statusO by remember { mutableIntStateOf(comingStatusO) }

    val handleClick = { clickedPosition: Int ->
        var moveMade = false
        cells = cells.mapIndexed { index, value ->
            if(!isGameEnd && clickedPosition == index && cells[index].dataText.isBlank()) {
                moveMade = true
                value.copy(dataText = getCurrentPlayer(currentPlayer))
            } else {
                value
            }
        }
        if (isWin(cells, getCurrentPlayer(currentPlayer))) {
            isGameEnd = true
            if (currentPlayer) {
                statusX += 1
            } else {
                statusO += 1
            }
        }
        if(isDraw(cells)) {
            statusDraw += 1
        }
        if (moveMade && !isWin(cells, getCurrentPlayer(currentPlayer))) {
            currentPlayer = !currentPlayer
        }
    }

    val restart = {
        currentPlayer = true
        isGameEnd = false
        cells = cells.map { cells ->
            cells.copy(dataText = "")
        }
    }
    PlayScreen(
        statusX = statusX,
        statusO = statusO,
        statusDraw = statusDraw,
        handleClick = handleClick,
        cells = cells,
        currentPlayer = currentPlayer,
        isGameEnd = isGameEnd,
        restart = restart,
        onNavigateEnd = onNavigateEnd
    )
}
@Composable
fun PlayScreen(
    statusX: Int,
    statusO: Int,
    statusDraw: Int,
    restart: () -> Unit,
    handleClick: (Int) -> Unit,
    cells: List<Data>,
    currentPlayer: Boolean,
    isGameEnd: Boolean,
    onNavigateEnd: (Int, Int, Int) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color.Black else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 35.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(0.dp, 80.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.W500,
            color = Color(121, 134, 203, 255)
        )
        Column {
            Box(
                modifier = Modifier
                    .size(340.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
                    .background(Color(3, 169, 244, 255))
            ) {
                dataCells.forEachIndexed { index, cell ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .offset(cell.x, cell.y)
                            .clip(CircleShape)
                            .background(if(darkMode) Color(252, 215, 215, 255) else Color(209, 238, 200, 255))
                            .border(1.dp, Color.Black, CircleShape)
                            .clickable { handleClick(index) }
                    ) {
                        Text(
                            text = cells[index].dataText,
                            fontSize = 55.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = when {
                    isGameEnd -> "Winner is ${getCurrentPlayer(currentPlayer)}"
                    isDraw(cells) -> "Draw"
                    else -> "Move Player of ${getCurrentPlayer(currentPlayer)}"
                },
                fontSize = 20.sp
            )
            if (isGameEnd || isDraw(cells)) {
                onNavigateEnd(statusX, statusO, statusDraw)
                restart()
            }
        }
    }
}
@Composable
fun EndScreen(
    statusX: Int,
    statusO: Int,
    statusDraw: Int,
    onNavigateRestart: () -> Unit,
    onNavigateMainMenu: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color.Black else Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = "X: $statusX",
                        fontSize = 35.sp,
                        color = if(darkMode) Color.White else Color.Black
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "O: $statusO",
                        fontSize = 35.sp,
                        color = if(darkMode) Color.White else Color.Black
                    )
                }
                Text(
                    text = "Draw: $statusDraw",
                    fontSize = 35.sp,
                    color = if(darkMode) Color.White else Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .width(175.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(7.dp))
                .border(
                    width = 2.dp,
                    color = Color(155, 57, 57, 255),
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable(onClick = onNavigateRestart),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Restart",
                fontSize = 25.sp,
                color = if(darkMode) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .width(175.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(7.dp))
                .border(
                    width = 2.dp,
                    color = Color(173, 94, 11, 255),
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable(onClick = onNavigateMainMenu),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Go to main menu",
                fontSize = 20.sp,
                color = if(darkMode) Color.White else Color.Black
            )
        }
    }
}
@Composable
fun Menu(
    onNavigatePlay: () -> Unit,
    onNavigateSettings: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color.Black else Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Play",
            fontSize = 20.sp,
            fontWeight = FontWeight.W400,
            color = if(darkMode) Color.White else Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(7.dp))
                .border(2.dp, if(darkMode) Color.White else Color.LightGray, RoundedCornerShape(7.dp))
                .clickable(onClick = onNavigatePlay)
        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "start",
                modifier = Modifier.fillMaxSize(),
                tint = if(darkMode) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Settings(onNavigateSettings)
    }
}
@Composable
fun SettingScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(if(darkMode) Color.Black else Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .width(45.dp)
                .height(25.dp)
                .clip(RoundedCornerShape(7.dp))
                .border(2.dp, if(darkMode) Color.White else Color.Gray, RoundedCornerShape(7.dp))
                .clickable(onClick = onBack)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.fillMaxSize(),
                tint = if(darkMode) Color.White else Color.Black
            )
        }
        Switch(
            modifier = Modifier.align(Alignment.CenterHorizontally).size(60.dp),
            checked = darkMode,
            onCheckedChange = { darkMode = !darkMode },
            colors = SwitchDefaults.colors(
                checkedIconColor = Color.Red,
                checkedTrackColor = Color.Blue,
                checkedThumbColor = Color.Green,
                checkedBorderColor = Color.LightGray,
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color(149, 85, 85, 255),
                uncheckedBorderColor = Color.Magenta
            ),
        )
    }
}
@Composable
fun Settings(
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(RoundedCornerShape(7.dp))
            .border(2.dp, if(darkMode) Color.White else Color.LightGray, RoundedCornerShape(7.dp))
            .clickable(onClick = onNavigate)
    ) {
        Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = "setting",
            modifier = Modifier.fillMaxSize(),
            tint = if(darkMode) Color.White else Color.Black
        )
    }
}

//sealed class Routes(val route: String) {
//    data object Home : Routes("Home")
//    data object Settings : Routes("Settings")
//    data object TicTacToe : Routes("TicTacToe") {
//        fun getString2() = "TicTacToe/{statusX}/{statusO}/{statusDraw}"
//        fun getString(
//            statusX: Int = 0,
//            statusO: Int = 0,
//            statusDraw: Int = 0
//        ) = "$route/$statusX/$statusO/$statusDraw"
//    }
//}
// Menu
// SettingScreen
// TicTacToe/{statusX}/{statusO}/{statusDraw}
// EndScreen/{statusX}/{statusO}/{statusDraw}

@Serializable
object Menu

@Serializable
object SettingScreen

@Serializable
data class TicTacToe(
    val statusX: Int = 0,
    val statusO: Int = 0,
    val statusDraw: Int = 0
)

@Serializable
data class EndScreen(
    val statusX: Int,
    val statusO: Int,
    val statusDraw: Int
)

@Preview(showBackground = true)
@Composable
fun ApplicationPreview() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Menu
    ) {
        composable<Menu> {
            Menu(
                onNavigatePlay = {
                    navController.navigate(TicTacToe())
                },
                onNavigateSettings = {
                    navController.navigate(SettingScreen)
                }
            )
        }
        composable<SettingScreen> {
            SettingScreen {
                navController.popBackStack()
            }
        }
        composable<TicTacToe> { backStackEntry ->
            val status = backStackEntry.toRoute<TicTacToe>()

            TicTacToe(
                comingStatusX = status.statusX,
                comingStatusO = status.statusO,
                comingStatusDraw = status.statusDraw,
                onNavigateEnd = { x: Int, o: Int, draw: Int ->
                    navController.navigate(EndScreen(x, o, draw))
                }
            )
        }
        composable<EndScreen> { backStackEntry ->
            val status = backStackEntry.toRoute<EndScreen>()

            EndScreen(
                statusX = status.statusX,
                statusO = status.statusO,
                statusDraw = status.statusDraw,
                onNavigateRestart = {
                    navController.navigate(TicTacToe(status.statusX, status.statusO, status.statusDraw)) {
                        popUpTo(TicTacToe(status.statusX, status.statusO, status.statusDraw)) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateMainMenu = {
                    navController.navigate(Menu) {
                        popUpTo(Menu) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}