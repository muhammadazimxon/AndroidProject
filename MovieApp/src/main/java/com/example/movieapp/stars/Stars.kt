package com.example.movieapp.stars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainStar(viewModel: StarViewModel = viewModel(), darkMode: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            viewModel.stars.forEachIndexed { index, star ->
                Star(
                    star = star,
                    onClick = { viewModel.eventHandler(ActionEvent.StarChangeAction(index)) },
                    darkMode = darkMode
                )
            }
        }
        Text(
            text = "${viewModel.quantity + 1} " + if(viewModel.quantity + 1 <= 1) "star" else "stars",
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            color = if(darkMode) Color.White else Color.DarkGray
        )
    }
}
@Composable
fun Star(
    star: Star,
    onClick: () -> Unit,
    darkMode: Boolean
) {
    IconButton(
        modifier = Modifier.size(50.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = if(darkMode) {
                if (star.isSelected)
                    Color.Red
                else
                    Color.Gray
            } else {
                if (star.isSelected)
                    Color.Red
                else
                    Color.DarkGray
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StarPreview() {
    MainStar(darkMode = false)
}