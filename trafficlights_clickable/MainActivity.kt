package com.example.trafficlights_clickable

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trafficlights_clickable.ui.theme.TrafficLights_ClickableTheme
// 0 - is red, 1 - is yellow, 2 - is green
// 1 -> 0 || 2
// 0 -> 1 else both false 2 true
// 2 -> 1 else both false 0 true
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var dataCells by remember {
                mutableStateOf(mutableListOf(
                    Data(color = Color.LightGray, isOn = false),
                    Data(color = Color.LightGray, isOn = false),
                    Data(color = Color.LightGray, isOn = false)
                ))
            }

            // turning Ways: (0 - red, 1 - yellow, 2 - green)
            // red -> if green || yellow is on reset green and yellow
            // yellow -> if we can turn any
            // green -> if red and yellow is on reset yellow and red
            // else -> nothing
            /*fun resetForRed(dataCells: List<Data>): List<Data>  {
                val temp = dataCells
                temp[1].color = Color.LightGray
                temp[2].color = Color.LightGray
                return temp
            }
            fun resetForGreen(dataCells: List<Data>): List<Data> {
                val temp = dataCells
                temp[0].color = Color.LightGray
                temp[1].color = Color.LightGray
                return temp
            }*/
            val handler = { clickedPosition: Int ->
                dataCells = dataCells.mapIndexed { index, value ->
                    when (index) {
                        clickedPosition -> {
                            when (clickedPosition) {
                                0 -> value.copy(color = Color.Red, isOn = true)
                                1 -> value.copy(color = Color.Yellow, isOn = true)
                                2 -> value.copy(color = Color.Green, isOn = true)
                                else -> value
                            }
                        }
                        else -> value
                    }
                    /*if(clickedPosition == index && dataCells[index].isOn) {
                            value
                        } else if(clickedPosition == index && !dataCells[index].isOn) {
                            if(dataCells[0].isOn && !dataCells[2].isOn) {
                                value.copy()
                            }
                        }*/
                }.toMutableList()
                if(dataCells[0].isOn && clickedPosition == 2) {
                    dataCells[0].isOn = false
                    dataCells[0].color = Color.LightGray
                    dataCells[1].isOn = false
                    dataCells[1].color = Color.LightGray
                } else if(dataCells[1].isOn && clickedPosition == 0) {
                    dataCells[0].isOn = true
                    dataCells[0].color = Color.Red
                    dataCells[2].isOn = false
                    dataCells[2].color = Color.LightGray
                    dataCells[1].isOn = false
                    dataCells[1].color = Color.LightGray
                } else if(clickedPosition == 1 && dataCells[2].isOn) {
                    dataCells[2].isOn = false
                    dataCells[2].color = Color.LightGray
                } else if(dataCells[1].isOn && clickedPosition == 2) {
                    dataCells[2].isOn = true
                    dataCells[2].color = Color.Red
                    dataCells[0].isOn = false
                    dataCells[0].color = Color.LightGray
                    dataCells[1].isOn = false
                    dataCells[1].color = Color.LightGray
                } else if(dataCells[2].isOn && clickedPosition == 0) {
                    dataCells[2].isOn = false
                    dataCells[2].color = Color.LightGray
                    dataCells[1].isOn = false
                    dataCells[1].color = Color.LightGray
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .height(500.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(Color.Gray)
                        .border(2.dp, Color.Black, RoundedCornerShape(17.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(125.dp)
                                .clip(CircleShape)
                                .background(dataCells[0].color)
                                .border(1.dp, Color.Black, CircleShape)
                                .clickable { handler(0) }
                        )
                        Box(
                            modifier = Modifier
                                .size(125.dp)
                                .clip(CircleShape)
                                .background(dataCells[1].color)
                                .border(1.dp, Color.Black, CircleShape)
                                .clickable { handler(1) }
                        )
                        Box(
                            modifier = Modifier
                                .size(125.dp)
                                .clip(CircleShape)
                                .background(dataCells[2].color)
                                .border(1.dp, Color.Black, CircleShape)
                                .clickable { handler(2) }
                        )
                    }
                }
            }
        }
    }
}

data class Data(
    var color: Color,
    var isOn: Boolean
)
@Composable
fun Greeting() {
    var dataCells by remember {
        mutableStateOf(mutableListOf(
            Data(color = Color.LightGray, isOn = false),
            Data(color = Color.LightGray, isOn = false),
            Data(color = Color.LightGray, isOn = false)
        ))
    }

    // turning Ways: (0 - red, 1 - yellow, 2 - green)
    // red -> if green || yellow is on reset green and yellow
    // yellow -> if we can turn any
    // green -> if red and yellow is on reset yellow and red
    // else -> nothing
    /*fun resetForRed(dataCells: List<Data>): List<Data>  {
        val temp = dataCells
        temp[1].color = Color.LightGray
        temp[2].color = Color.LightGray
        return temp
    }
    fun resetForGreen(dataCells: List<Data>): List<Data> {
        val temp = dataCells
        temp[0].color = Color.LightGray
        temp[1].color = Color.LightGray
        return temp
    }*/
    val handler = { clickedPosition: Int ->
        dataCells = dataCells.mapIndexed { index, value ->
            when (index) {
                clickedPosition -> {
                    when (clickedPosition) {
                        0 -> value.copy(color = Color.Red, isOn = true)
                        1 -> value.copy(color = Color.Yellow, isOn = true)
                        2 -> value.copy(color = Color.Green, isOn = true)
                        else -> value
                    }
                }
                else -> value
            }
            /*if(clickedPosition == index && dataCells[index].isOn) {
                    value
                } else if(clickedPosition == index && !dataCells[index].isOn) {
                    if(dataCells[0].isOn && !dataCells[2].isOn) {
                        value.copy()
                    }
                }*/
        }.toMutableList()
        if(dataCells[0].isOn && clickedPosition == 2) {
            dataCells[0].isOn = false
            dataCells[0].color = Color.LightGray
            dataCells[1].isOn = false
            dataCells[1].color = Color.LightGray
        } else if(dataCells[1].isOn && clickedPosition == 0) {
            dataCells[0].isOn = true
            dataCells[0].color = Color.Red
            dataCells[2].isOn = false
            dataCells[2].color = Color.LightGray
            dataCells[1].isOn = false
            dataCells[1].color = Color.LightGray
        } else if(clickedPosition == 1 && dataCells[2].isOn) {
            dataCells[2].isOn = false
            dataCells[2].color = Color.LightGray
        }
        else if(dataCells[1].isOn && clickedPosition == 2) {
            dataCells[2].isOn = true
            dataCells[2].color = Color.Red
            dataCells[0].isOn = false
            dataCells[0].color = Color.LightGray
            dataCells[1].isOn = false
            dataCells[1].color = Color.LightGray
        } else if(dataCells[2].isOn && clickedPosition == 0) {
            dataCells[2].isOn = false
            dataCells[2].color = Color.LightGray
            dataCells[1].isOn = false
            dataCells[1].color = Color.LightGray
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(500.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(Color.Gray)
                .border(2.dp, Color.Black, RoundedCornerShape(17.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                        .background(dataCells[0].color)
                        .border(1.dp, Color.Black, CircleShape)
                        .clickable { handler(0) }
                )
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                        .background(dataCells[1].color)
                        .border(1.dp, Color.Black, CircleShape)
                        .clickable { handler(1) }
                )
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                        .background(dataCells[2].color)
                        .border(1.dp, Color.Black, CircleShape)
                        .clickable { handler(2) }
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun GreetingPreview() {
    Greeting()
}