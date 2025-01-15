package com.example.todoproject.components.upperContainer.upperContainerComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldSide(
    input: String,
    inputOnEnter: (String) -> Unit
) {
    var text = input
    Row {
        TextField(
            value = text,
            modifier = Modifier
                .width(300.dp)
                .height(55.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(7.dp)),
            onValueChange = {
                text = it
                inputOnEnter(it)
            },
            shape = RoundedCornerShape(7.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(70, 138, 103, 255),
                unfocusedContainerColor = Color(70, 138, 103, 255),
                cursorColor = Color.Black,
                unfocusedTextColor = Color.White
            ),
            placeholder = { Text(text = "Enter text", fontWeight = FontWeight.W500, color = Color.White) }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TextFiledSidePreview() {
}