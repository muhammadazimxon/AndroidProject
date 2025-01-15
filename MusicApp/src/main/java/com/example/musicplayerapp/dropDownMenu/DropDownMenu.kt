package com.example.musicplayerapp.dropDownMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            DropdownMenu(
                expanded = expanded,
                containerColor = Color.White,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Settings", color = Color.Blue) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings",
                            tint = Color.Gray
                        )
                    },
                    onClick = { } // Event
                )
                DropdownMenuItem(
                    text = { Text("Delete", color = Color.Red) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = Color.Red
                        )
                    },
                    onClick = { } // Event
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    DropDownMenu()
}