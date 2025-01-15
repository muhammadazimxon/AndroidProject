package com.example.todoproject.components.upperContainer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoproject.components.Task
import com.example.todoproject.components.taskContainer.Urgency
import com.example.todoproject.components.upperContainer.upperContainerComponents.AddButton
import com.example.todoproject.components.upperContainer.upperContainerComponents.TextFieldSide
import com.example.todoproject.components.upperContainer.upperContainerComponents.UrgencyContainer

@Composable
fun UpperContainer(
    inputOnTextField: String,
    importance: String,
    inputOnEnter: (String) -> Unit,
    addOnClick: (Urgency) -> Unit,
    listOfUrgency: List<Urgency>,
    handlerOnChoose: (String) -> Unit,
    isAddOnClick: Boolean
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldSide(
                input = inputOnTextField,
                inputOnEnter = inputOnEnter
            )
            AddButton(
                isAddOnClick = isAddOnClick,
                addOnClick = addOnClick,
                urgency = when(importance) {
                    "PALTRY" -> Urgency.PALTRY
                    "NORMAL" -> Urgency.NORMAL
                    "URGENT" -> Urgency.URGENT
                    else -> Urgency.PALTRY
                }
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UrgencyContainer(importance, listOfUrgency, handlerOnChoose)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun UpperContainerPreview() {
    var filter by remember { mutableStateOf("") }
    val listOfUrgency = listOf(
        Urgency.PALTRY,
        Urgency.NORMAL,
        Urgency.URGENT
    )
    val handlerOnChoose = { text: String ->
        filter = text
    }
    var input = ""
    val inputOnEnter = { text: String ->
        input = text
    }
    val addOnClick = { importance: Urgency ->
//        listOfTasks = (listOfTasks + Task(text = input, urgency = importance, id = generateID(), isSelected = false)).toMutableList()
    }
//    UpperContainer(
////        filter = filter,
//        listOfUrgency = listOfUrgency,
//        handlerOnChoose = handlerOnChoose,
//        inputOnEnter = inputOnEnter,
//        addOnClick = addOnClick,
//        inputOnTextField = TODO(),
//        importance = TODO(),
////        inputOnTextField = ""
//    )
}