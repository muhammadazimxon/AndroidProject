package com.example.todoproject.components.upperContainer.upperContainerComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoproject.components.taskContainer.Urgency

@Composable
fun AddButton(
    isAddOnClick: Boolean,
    urgency: Urgency,
    addOnClick: (Urgency) -> Unit
) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(72, 147, 223, 255))
            .clickable(onClick = { addOnClick(urgency) }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            if(isAddOnClick) Icons.Rounded.Add else Icons.Rounded.Edit,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddButtonPreview() {
}