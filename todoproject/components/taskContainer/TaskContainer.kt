package com.example.todoproject.components.taskContainer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.components.Task

@Composable
fun TaskContainer(
    task: Task,
    onDelete: (Int) -> Unit,
    checkBoxOnChange: (Boolean, Int) -> Unit,
    onEdit: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
            .padding(end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isSelected,
                onCheckedChange = { checkBoxOnChange(it, task.id) }, // checkbox
                colors = CheckboxDefaults.colors(
                )
            )
            Column {
                Text(
                    text = task.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = when(task.urgency) {
                        Urgency.PALTRY -> Color(88, 170, 55, 255)
                        Urgency.NORMAL -> Color(55, 91, 170, 255)
                        Urgency.URGENT -> Color(208, 71, 71, 255)
                    }
                )
                Text(
                    text = task.urgency.toString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W500,
                    color = when(task.urgency) {
                        Urgency.PALTRY -> Color(88, 170, 55, 255)
                        Urgency.NORMAL -> Color(55, 91, 170, 255)
                        Urgency.URGENT -> Color(208, 71, 71, 255)
                    }
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TodoButton(
                size = DpSize(45.dp, 35.dp),
                color = Color.Blue,
                onClick = { onEdit(task.id) } // edit
            ) {
                Icon(
                    Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            TodoButton(
                size = DpSize(45.dp, 35.dp),
                color = Color.Red,
                onClick = { onDelete(task.id) } // delete
            ) {
                Icon(
                    Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TaskContainerPreview() {
//    var tasks by remember { mutableStateOf(listOfTasks) }
//    val onDelete = { id: Int ->
//        listOfTasks = listOfTasks.filter { it.id != id }.toMutableList()
//        tasks = listOfTasks
//    }
//    TaskContainer(
//        onDelete = onDelete,
//        task = tasks[2],
//        checkBoxOnChange = {
//        }
//    )
}