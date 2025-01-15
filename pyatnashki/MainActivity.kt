package com.example.pyatnashki

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// [[1], [2], [3], []]
// [[2], [],
//  [3], [1]]
// [[1], [], [3], [2]]
// block # 87.5

// 1 15  14 13
// 12 11 10 9
// 8  7  6  5
// 4  3  2 []  listof(0) + listof(1,2,3) = listof(0,1,2,3)

// ways to click:
//i: 0    1     2     3
// [[], [2\], [3\], [1]]
// [[1\], [], [2], [3\]]
// [[1\], [2], [], [3\]]
// [[1], [2\], [3\], []]
// 4 x 4
// [[[1], [2], [4], [7]]
// [[8], [5], [3], [9]]
//  [[6], [], [11], [13]]
//  [[10], [12], [14], [15]]]

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting()
        }
    }
}
data class Direction(
    val x: Dp,
    val y: Dp
)
val positions = (0..15).map { index ->
    Direction(
        x = ((index % 4) * 97.5).dp + 10.dp,
        y = ((index / 4) * 97.5).dp + 10.dp
    )
}
val winCase = listOf(
    listOf(1, 2, 3, 4),
    listOf(5, 6, 7, 8),
    listOf(9, 10, 11, 12),
    listOf(13, 14, 15, 0)
)
fun isWin(start: List<List<Int>>): Boolean {
    return winCase.flatten() == start.flatten()
}
fun isCorrectMove(start: List<List<Int>>): List<List<Boolean>> {
    return start.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, value ->
            value == winCase[rowIndex][colIndex]
        }
    }
}
// list[index][clickedColumn] == 0 for col | list[clickedRow][index] for row == 0
//clickedRow clickedColumn
// 0[x,y]
var counterOfMove = 0
fun zerosPosition(start: List<List<Int>>): Pair<Int, Int> {
    // 1 0
    start.forEachIndexed { rowIndexed, row ->
        row.forEachIndexed { colIndex, _ ->
            if(row[colIndex] == 0)
                return rowIndexed to colIndex
        }
    }
    return -1 to -1
}
fun check(clickedPosition: Int, start: List<List<Int>>): List<List<Int>> {
    val clickedRow = clickedPosition / 4
    val clickedCol = clickedPosition % 4
    val temp = start.map { it.toMutableList() }
    val (row, col) = zerosPosition(start)

    // start[row][col]

    if(row == clickedRow) {
        if(clickedCol < col) {
            for (index in 0..col) {
                if (temp[row][index] == 0) {
                    for (down in index downTo clickedCol + 1) {
                        temp[row][down] = temp[row][down - 1]
                        counterOfMove += 1
                    }
                    temp[row][clickedCol] = 0
                }
            }
        } else if(clickedCol > col) {
            for (down in col downTo 0) {
                if (temp[row][down] == 0) {
                    for (index in down..<clickedCol) {
                        temp[row][index] = temp[row][index + 1]
                        counterOfMove += 1
                    }
                    temp[row][clickedCol] = 0
                }
            }
        }
    } else if(col == clickedCol) {
        if(clickedRow < row) {
            for(index in 0..row) {
                if(temp[index][col] == 0) {
                    for(down in index downTo clickedRow + 1) {
                        temp[down][col] = temp[down - 1][col]
                        counterOfMove += 1
                    }
                    temp[clickedRow][col] = 0
                }
            }
        } else {
            for(down in row downTo 0) {
                if(temp[down][col] == 0) {
                    for(index in down..<clickedRow) {
                        temp[index][col] = temp[index + 1][col]
                        counterOfMove += 1
                    }
                    temp[clickedRow][col] = 0
                }
            }
        }
    }
    return temp.map { it.toList() }
}
@Composable
fun Greeting() {
    var counter by remember { mutableIntStateOf(0) }
    var start by remember { mutableStateOf(
        listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 0, 15)
        )
    ) }
    var correctness by remember {
        mutableStateOf(
            listOf(
                listOf(true, true, true, true),
                listOf(true, true, true, true),
                listOf(true, true, true, true),
                listOf(true, true, false, false),
            )
        )
    }
    val restart = {
        start = start.flatten().shuffled().chunked(4)
        correctness = isCorrectMove(start)
        counterOfMove = 0
        counter = 0
    }
    val handler = { clickedPosition: Int ->
        start = check(clickedPosition = clickedPosition, start = start)
        correctness = isCorrectMove(start)
        counter = counterOfMove
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Move $counter",
            fontSize = 25.sp
        )
        Text(
            text = "Sliding puzzle",
            fontSize = 40.sp,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(20.dp))
        if(isWin(start)) {
            Text(
                text = "You Won!",
                fontSize = 35.sp,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Box(
            modifier = Modifier
                .size(400.dp)
                .border(2.dp, Color.Gray)
        ) {
            start.flatten().forEachIndexed { index, value ->
                val row = index / 4
                val col = index % 4
                val color = if (correctness[row][col]) Color(75, 178, 101, 255) else Color.LightGray
                Box(
                    modifier = Modifier
                        .size(87.5.dp)
                        .offset(positions[index].x, positions[index].y)
                        .background(if (value == 0) Color.Unspecified else color)
                        .clickable(onClick = { if (!isWin(start)) handler(index) }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(value == 0) "" else value.toString(),
                        fontWeight = FontWeight.W500,
                        fontSize = 35.sp
                    )
                }
            }
        }


        // 0 3 4 6 8 9 3
        // change: 9
        // case: 9
        Spacer(modifier = Modifier.height(15.dp))
        ElevatedButton(onClick = restart) {
            Text(
                text = "Restart"
            )
        }
    }
}
// 4 x 4
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun GreetingPreview() {
    Greeting()
}