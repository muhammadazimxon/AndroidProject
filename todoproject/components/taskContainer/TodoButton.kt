package com.example.todoproject.components.taskContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun TodoButton(
    size: DpSize,
    onClick: () -> Unit,
    color: Color,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = color,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
fun TodoButtonPreview() {
    TodoButton(
        size = DpSize(45.dp, 35.dp),
        color = Color.Blue,
        onClick = {}
    ) {
        Icon(
            Icons.Rounded.Edit,
            contentDescription = null,
            tint = Color.White
        )
    }
}