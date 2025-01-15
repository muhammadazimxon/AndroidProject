package com.example.movieapp.inside

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.BackButton
import com.example.movieapp.Movie
import com.example.movieapp.R
import com.example.movieapp.moviesDisplayer.MovieDisplayer
import com.example.movieapp.moviesDisplayer.RecommendationMovie
import com.example.movieapp.stars.MainStar

@Composable
fun MovieInfos(
    navController: NavController,
    id: Int,
    movies: List<Movie>,
    onBookMark: (Int) -> Unit,
    darkMode: Boolean
) {
    val movie = movies.find { it.id == id } ?: Movie(
        id = 4,
        name = "Красавчики до нашей эры",
        src = R.drawable.movie5,
        year = 2019,
        genre = "Комедии",
        description = "Один дома (Home Alone) — культовая семейная комедия"
    )// 0 means wrong image for wrong enter
    val recommendationMovies = movies.filter { it.genre == movie.genre && it.id != movie.id}
    var isElected by remember { mutableStateOf(movie.isElected) }
    BackButton(navController = navController, route = "Application")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if(darkMode) Color(26, 26, 26, 255) else Color.Transparent)
            .padding(top = 25.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 30.dp)
    ) {
        Image(
            painter = painterResource(movie.src),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = ("Название:  " + movie.name),
            color = if(darkMode) Color.White else Color.DarkGray,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = ("Описание:  " + movie.description),
            color = if(darkMode) Color.White else Color.DarkGray,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = ("Жанр:  " + movie.genre),
            color = if(darkMode) Color.White else Color.DarkGray,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = ("Год выпуска:  " + movie.year),
            color = if(darkMode) Color.White else Color.DarkGray,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainStar(darkMode = darkMode)
            Column() {
                IconButton(
                    onClick = {
                        onBookMark(id)
                        isElected = !isElected
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = if (isElected) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "bookmark",
                        modifier = Modifier.size(50.dp),
                        tint = if (darkMode) Color.White else Color.DarkGray
                    )
                }
                Text(
                    text = "Book mark",
                    fontSize = 11.5.sp,

                    color = if(darkMode) Color.White else Color.DarkGray
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Рекомендации:",
            fontSize = 15.sp,
            color = if(darkMode) Color.White else Color.DarkGray,
            modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(6.dp))
        RecommendationMovie(
            darkMode = darkMode,
            recommendedMovies = recommendationMovies,
            navController = navController
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun MovieInfosPreview() {
    val navController = rememberNavController()
    MovieInfos(navController = navController, id = 4, listOf(
        Movie(
            id = 4,
            name = "Красавчики до нашей эры",
            src = R.drawable.movie5,
            year = 2019,
            genre = "Комедии",
            description = "Один дома (Home Alone) — культовая семейная комедия, " +
                    "вышедшая в 1990 году, рассказывает о приключениях 8-летнего мальчика" +
                    " Кевина Маккаллистера, который случайно остается дома один на " +
                    "Рождество. Его семья в спешке улетает на отдых во Францию, забыв " +
                    "о нем. В одиночестве Кевин быстро адаптируется к новой свободе," +
                    " но его спокойствие нарушают двое неудачливых грабителей, Гарри " +
                    "и Марв."
        )),
        onBookMark = {},
        darkMode = true
    )
}