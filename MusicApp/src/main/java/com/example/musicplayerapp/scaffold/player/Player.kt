package com.example.musicplayerapp.scaffold.player

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayerapp.R
import com.example.musicplayerapp.dropDownMenu.DropDownMenu
import com.example.musicplayerapp.viewModel.MusicViewModel

@Composable
fun MusicViewModel.Player(
    id: Int,
    onBack: () -> Unit,
    context: Context
) {
    val music = musics.find { it.id == id } ?: wrongMusic
    var sliderValue by remember { mutableFloatStateOf(0.0f) }
    val duration = mediaPlayer?.duration?.toFloat() ?: 1f

    LaunchedEffect(mediaPlayer) {
        while (mediaPlayer?.isPlaying == true) {
            sliderValue = mediaPlayer!!.currentPosition.toFloat()
            kotlinx.coroutines.delay(100)
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            DropDownMenu()
        }
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(25.dp))
        Box(
            modifier = Modifier.width(300.dp)
                .height(400.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(music.image),
                contentDescription = "image",
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = music.musicName,
            fontSize = 25.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = music.author,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                IconButton(onClick = {}) { // onClick
                    Icon(
                        painter = painterResource(R.drawable.skip_previous),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { if(mediaIsPlaying) stopMusic() else playMusic(context = context) }) { // onClick
                    Icon(
                        painter = painterResource(if(mediaIsPlaying) R.drawable.pause_icon else R.drawable.play_arrow),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) { // onClick
                    Icon(
                        painter = painterResource(R.drawable.skip_next),
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Slider(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = sliderValue,
                valueRange = 0f..duration,
                onValueChange = {
                    sliderValue = it
                    capacitor = it
                    if (mediaIsPlaying) {
                        mediaPlayer!!.seekTo(sliderValue.toInt())
                    }
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color(153, 92, 60, 255),
                    activeTrackColor = Color(153, 92, 60, 255),
                    inactiveTrackColor = Color.LightGray,
                    inactiveTickColor = Color(153, 92, 60, 255),
                    activeTickColor = Color(153, 92, 60, 255)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerPreview() {
    MusicViewModel().Player(id = 1, onBack = {}, context = LocalContext.current)
}