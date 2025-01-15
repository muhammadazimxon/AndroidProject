package com.example.musicplayerapp.scaffold.playerList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayerapp.dropDownMenu.DropDownMenu
import com.example.musicplayerapp.viewModel.MusicViewModel

@Composable
fun MusicViewModel.PlayerList(contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(contentPadding)
    ) {
       items(musics) {
           ListItem(
               modifier = Modifier.clickable(onClick = { selectHandle(it.id) }), // onClick
               headlineContent = {
                   Text(
                       text = it.musicName,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis
                   )
               },
               supportingContent = {
                   Text(
                       text = it.author,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis
                   )
               },
               trailingContent = {
                   DropDownMenu()
               },
               leadingContent = {
                   Image(
                       modifier = Modifier.size(70.dp).clip(shape = CircleShape),
                       painter = painterResource(it.image),
                       contentDescription = null
                   )
               }
           )
       }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListPreview() {
    val playerList = MusicViewModel()
    playerList.PlayerList(contentPadding = PaddingValues(1.dp))
}