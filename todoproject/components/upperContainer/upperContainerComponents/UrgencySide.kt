package com.example.todoproject.components.upperContainer.upperContainerComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.components.taskContainer.Urgency

@Composable
fun UrgencyContainer(
    text: String,
    listOfUrgency: List<Urgency>,
    handlerOnChoose: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOfUrgency.forEach {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(7.dp))
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(7.dp))
                    .weight(1f)
                    .background(
                        when (it.name) {
                            Urgency.PALTRY.name -> if(it.name == text) Color(88, 170, 55, 255) else Color.Transparent
                            Urgency.NORMAL.name -> if(it.name == text) Color(55, 91, 170, 255) else Color.Transparent
                            Urgency.URGENT.name -> if(it.name == text) Color(208, 71, 71, 255) else Color.Transparent
                            else -> Color.Transparent
                        }
                    )
                    .clickable(onClick = { handlerOnChoose(it.name) }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W400,
                    color = if(text == it.name) Color.White else Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UrgencyContainerPreview() {
    var filter by remember { mutableStateOf("") }
    val listOfUrgency = listOf(
        Urgency.PALTRY,
        Urgency.NORMAL,
        Urgency.URGENT
    )
    val handlerOnChoose = { text: String ->
        filter = text
    }
    UrgencyContainer(filter, listOfUrgency, handlerOnChoose)
}