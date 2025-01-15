package com.example.movieapp.moviesDisplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.Movie
import com.example.movieapp.R

@Composable
fun MovieDisplayer(
    darkMode: Boolean,
    movies: List<Movie>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
            .padding(top = 25.dp)
            .clip(RoundedCornerShape(15.dp)),
    ) {
        items(movies) {
            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = if(!darkMode) 3.dp else 0.dp,
                        spotColor = if(!darkMode) Color.Black else Color.Transparent,
                    )
                    .clip(RoundedCornerShape(1.dp))
                    .padding(5.dp)
                    .background(if(darkMode)Color(105, 101, 83, 255) else Color.Transparent)
                    .padding(13.dp)
                    .clickable(onClick = {
                        navController.navigate("MovieInfos/${it.id}")
                    })
            ) {
                Image(
                    painter = painterResource(it.src),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Text(
                    text = it.name,
                    color = if(darkMode) Color.White else Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDisplayerPreview() {
    MovieDisplayer(true, listOf(Movie(
        id = 14,
        name = "Храброе сердце",
        src = R.drawable.movie15,
        year = 1995,
        genre = "Исторический эпос, Драма",
        description = "«Храброе сердце» — американская историческая драма режиссёра" +
                " и актёра Мэла Гибсона, вышедшая в 1995 году. Фильм рассказывает историю Вильяма Уоллеса," +
                " известного шотландского национального героя, который борется за независимость своей страны от" +
                " английского владычества. Картина получила множество наград, включая 5 премий «Оскар», включая" +
                " лучший фильм и лучшую режиссуру."
    )), rememberNavController())
}