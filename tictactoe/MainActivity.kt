package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
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
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting()
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
fun Greeting() {
    var cells by remember {
        mutableStateOf(listOf(
            Data(""), Data(""), Data(""),
            Data(""), Data(""), Data(""),
            Data(""), Data(""), Data("")
        ))
    }
    var currentPlayer by remember { mutableStateOf(true) }
    var isGameEnd by remember { mutableStateOf(false) }
    var isDraw by remember { mutableStateOf(false) }
    var statsOfX by remember { mutableIntStateOf(0) }
    var statsOfO by remember { mutableIntStateOf(0) }

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
                statsOfX += 1
            } else {
                statsOfO += 1
            }
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 35.sp,
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(0.dp, 80.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.W500,
            color = Color(121, 134, 203, 255)
        )
        Row(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(7.dp, 140.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = "X: $statsOfX",
                fontSize = 35.sp,
                color = Color(121, 134, 203, 255)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "O: $statsOfO",
                fontSize = 35.sp,
                color = Color(121, 134, 203, 255)
            )
        }
        Column {
            Box(
                modifier = Modifier
                    .size(340.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(15.dp))
                    .background(Color.LightGray)
            ) {
                dataCells.forEachIndexed { index, cell ->
                    Box(
                        modifier = Modifier.size(100.dp)
                            .offset(cell.x, cell.y)
                            .clip(CircleShape)
                            .background(Color.Gray)
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
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier.width(75.dp).height(35.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(187, 173, 160))
                        .clickable { restart() }
                ) {
                    Text(
                        text = "Restart",
                        fontSize = 17.sp,
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun GreetingPreview() {
    Greeting()
}