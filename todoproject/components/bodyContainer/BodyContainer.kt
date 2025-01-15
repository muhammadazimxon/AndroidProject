package com.example.todoproject.components.bodyContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.components.FilterType
import com.example.todoproject.components.Task
import com.example.todoproject.components.taskContainer.Urgency

val filterChoice = listOf(
    "All",
    "Active",
    "Completed"
)

@Composable
fun BodyContainer(
    filterTask: FilterType,
    onClickChange: (FilterType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        filterChoice.forEach {
            Box(
                modifier = Modifier.height(30.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .clickable(onClick = { onClickChange(
                        when(it) {
                            "All" -> FilterType.ALL
                            "Active" -> FilterType.ACTIVE
                            "Completed" -> FilterType.COMPLETED
                            else -> FilterType.ALL
                        }
                    ) })
                    .weight(1f)
                    .border(2.dp, Color.Black, RoundedCornerShape(9.dp))
                    .background(
                        when {
                            it == "All" && filterTask.name == it.uppercase() -> Color(49, 156, 163, 255)
                            it == "Active" && filterTask.name == it.uppercase() -> Color(10, 166, 10, 255)
                            it == "Completed" && filterTask.name == it.uppercase() -> Color(85, 71, 208, 255)
                            else -> Color.Transparent
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    color = if(it == filterTask.name)
                        Color.White
                    else
                        Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyContainerPreview() {
}