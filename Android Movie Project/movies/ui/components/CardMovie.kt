package com.example.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.movies.R

@Composable
fun CardMovie(
    url: String,
    limit: Int
) {
    Box(
        modifier = Modifier
            .height(230.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color(1f, 1f, 1f, .5f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .placeholder(R.drawable.placeholder)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "$limit+",
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(1f, 1f, 1f, .6f))
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CardMoviePreview() {
    CardMovie(
        url = "https://avatars.mds.yandex.net/i?id=302852fce5364ee937b70df16b80fcd1db50103241170a99-3612431-images-thumbs&n=13",
        limit = 16
    )
}