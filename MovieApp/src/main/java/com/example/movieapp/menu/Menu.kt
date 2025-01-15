package com.example.movieapp.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Menu(
    darkMode: Boolean,
    offset: Dp,
    onFavourite: () -> Unit,
    onSettings: () -> Unit,
    onDarkMode: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(72.dp)
            .offset(y = offset)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .shadow(
                elevation = 1.dp,
                ambientColor = Color.Black
            )
            .background(if(darkMode) Color(66, 67, 67, 255) else Color.White)
            .border(2.dp, if(darkMode) Color(244, 67, 54, 255) else Color.DarkGray, RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
        ) {
        IconButton(
            onClick = onFavourite,
            modifier = Modifier.size(100.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountBox,
                contentDescription = "bookmark",
                modifier = Modifier.size(50.dp)
            )
        }
        IconButton(
            onClick = onSettings,
            modifier = Modifier.size(100.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = "settings",
                modifier = Modifier.size(50.dp),
                tint = if(darkMode) Color.Black else Color.DarkGray
            )
        }
        Switch(
            checked = darkMode,
            onCheckedChange = onDarkMode,
            colors = SwitchDefaults.colors(
                checkedIconColor = Color.DarkGray,
                checkedTrackColor = Color.Red,
                uncheckedTrackColor = Color(139, 134, 145, 255),
                uncheckedThumbColor = Color.White
            ),
            modifier = Modifier.size(100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    Menu(
        offset = 0.dp,
        darkMode = false,
        onSettings = { },
        onDarkMode = { },
        onFavourite = { }
    )
}