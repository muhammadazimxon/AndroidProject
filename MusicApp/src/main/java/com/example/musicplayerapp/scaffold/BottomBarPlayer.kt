package com.example.musicplayerapp.scaffold

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayerapp.R
import com.example.musicplayerapp.scaffold.playerList.MusicPlayer
import com.example.musicplayerapp.viewModel.MusicViewModel

@Composable
fun BottomBarPlayer(
    music: MusicPlayer,
    viewModel: MusicViewModel,
    context: Context,
    isPlaying: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(70.dp),
                painter = painterResource(music.image),
                contentDescription = "currentPhoto"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(150.dp),
                    text = music.musicName,
                    fontSize = 23.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(150.dp),
                    text = music.author,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "previous"
                )
            }
            IconButton(
                onClick = { if(isPlaying) viewModel.stopMusic() else viewModel.playMusic(context = context) }
            ) {
                Icon(
                    painter = painterResource(if(isPlaying) R.drawable.pause_icon else R.drawable.play_arrow),
                    contentDescription = "play"
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_next),
                    contentDescription = "next"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPlayerPreview() {
    BottomBarPlayer(
        music = MusicViewModel().wrongMusic,
        viewModel = MusicViewModel(),
        context = LocalContext.current,
        isPlaying = false
    )
}