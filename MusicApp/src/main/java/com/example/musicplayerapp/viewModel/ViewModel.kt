package com.example.musicplayerapp.viewModel

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.musicplayerapp.R
import com.example.musicplayerapp.scaffold.playerList.MusicPlayer

class MusicViewModel : ViewModel() {
    var expanded by mutableStateOf(false)
    var darkMode: Boolean by mutableStateOf(true)
    var capacitor by mutableFloatStateOf(0.0f)
    val wrongMusic = MusicPlayer(
        author = "Unknown",
        musicName = "Wrong",
        image = R.drawable.ic_launcher_background,
        audio = R.raw.num4,
        id = 0
    )
    var mediaIsPlaying by mutableStateOf(false)
    private var idCounter = 0
    private fun generateID() = idCounter++
    var selectedId by mutableIntStateOf(0)
    val musics = listOf(
        MusicPlayer(
            author = "Ludwig van Beethoven",
            musicName = "Symphony No. 9",
            image = R.drawable.beetgoven,
            audio = R.raw.symphony_no9,
            id = generateID()
        ),
        MusicPlayer(
            author = "Wolfgang Amadeus Mozart",
            musicName = "Eine kleine Nachtmusik",
            image = R.drawable.gabrielmavik,
            audio = R.raw.glibri_mavik,
            id = generateID()
        ),
        MusicPlayer(
            author = "Johann Sebastian Bach",
            musicName = "Toccata and Fugue in D Minor",
            image = R.drawable.num3,
            audio = R.raw.qair3,
            id = generateID()
        ),
        MusicPlayer(
            author = "Pyotr Ilyich Tchaikovskya",
            musicName = "Swan Lake",
            image = R.drawable.num4,
            audio = R.raw.num4,
            id = generateID()
        )
    )

    var mediaPlayer: MediaPlayer? = null
    var duration = mediaPlayer?.duration ?: 0
    @OptIn(UnstableApi::class)
    fun playMusic(context: Context) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        val selectedMusic = musics.find { it.id == selectedId } ?: return
        mediaIsPlaying = true
        mediaPlayer = MediaPlayer.create(context, selectedMusic.audio).apply {
            start()
            seekTo(capacitor.toInt())
            this@MusicViewModel.duration = duration
            setOnErrorListener { _, what, extra ->
                Log.e("MusicPlayer", "Error playing music: $what, $extra")
                true
            }
        }

    }

    fun stopMusic() {
        mediaPlayer?.let {
            duration = it.duration
            it.stop()
            it.release()
        }
        mediaIsPlaying = false
        mediaPlayer = null
    }
    fun selectHandle(id: Int) {
        selectedId = id
    }
}