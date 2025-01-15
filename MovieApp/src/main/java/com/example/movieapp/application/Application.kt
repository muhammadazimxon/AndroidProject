package com.example.movieapp.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.Movie
import com.example.movieapp.R
import com.example.movieapp.inside.MovieInfos
import com.example.movieapp.menu.Menu
import com.example.movieapp.menu.buttons.Elected
import com.example.movieapp.menu.buttons.Setting
import com.example.movieapp.moviesDisplayer.MovieDisplayer

// change MovieInfos for dark mode

sealed class MovieEvent() {
    data class BookMarkEvent(val id: Int) : MovieEvent()
    data class DarkModeEvent(val isDarkMode: Boolean) : MovieEvent()
}

class MovieViewModel : ViewModel() {
    var movies = listOf(
        Movie(
            id = 1,
            name = "Один дома",
            src = R.drawable.movie2,
            year = 1990,
            genre = "Комедии",
            description = "Один дома (Home Alone) — культовая семейная комедия, " +
                    "вышедшая в 1990 году, рассказывает о приключениях 8-летнего мальчика" +
                    " Кевина Маккаллистера, который случайно остается дома один на " +
                    "Рождество. Его семья в спешке улетает на отдых во Францию, забыв " +
                    "о нем. В одиночестве Кевин быстро адаптируется к новой свободе," +
                    " но его спокойствие нарушают двое неудачливых грабителей, Гарри " +
                    "и Марв."
        ),
        Movie(
            id = 2,
            name = "Один дома 2: Затерянный в Нью-Йорке",
            src = R.drawable.movie3,
            year = 1992,
            genre = "Комедии",
            description = "Один дома 2: Затерянный в Нью-Йорке " +
                    "(Home Alone 2: Lost in New York) — продолжение " +
                    "популярной рождественской комедии, вышедшее в 1992 " +
                    "году. На этот раз Кевин Маккаллистер снова оказывается " +
                    "вдали от своей семьи, но теперь не дома, а в огромном Нью-Йорке."
        ),
        Movie(
            id = 3,
            name = "Гринч – похититель Рождества",
            src = R.drawable.movie4,
            year = 2000,
            genre = "Семейные",
            description = "Гринч – похититель Рождества (How the Grinch Stole Christmas) — " +
                    "яркая комедийная сказка, основанная на знаменитой книге Доктора Сьюза, рассказывающая" +
                    " историю зеленого ворчуна Гринча, который ненавидит Рождество. Гринч живет в пещере на " +
                    "вершине горы, недалеко от города Ктограда, где жители, Ктовильцы, обожают праздновать Рождество. " +
                    "Устав от их радостной суеты и громких праздников, Гринч решает украсть у них Рождество: он переодевается" +
                    " в Санта-Клауса и вместе со своим верным псом Максом спускается в город, чтобы украсть подарки, украшения" +
                    " и праздничный ужин."
        ),
        Movie(
            id = 4,
            name = "Красавчики до нашей эры",
            src = R.drawable.movie5,
            year = 2019,
            genre = "Комедии",
            description = "Красавчики до нашей эры (Year One) — комедийный фильм 2009 года, " +
                    "снятый в жанре приключенческой сатиры. Сюжет рассказывает о двух незадачливых" +
                    " охотниках-собирателях, Зеде и Охе, которые отправляются в эпическое путешествие " +
                    "после того, как их изгоняют из родного племени."
        ),
        Movie(
            id = 5,
            name = "Шрек",
            src = R.drawable.movie6,
            year = 2001,
            genre = "Мультфильмы",
            description = "Шрек (Shrek) — американский анимационный фильм 2001 года, " +
                    "который рассказывает историю зелёного огра, который должен защитить " +
                    "свою болота от захватчика — принца Фиона, который хочет его владение. " +
                    "Сюжет также касается дружбы, принятия себя и любви."
        ),
        Movie(
            id = 6,
            name = "Титаник",
            src = R.drawable.movie7,
            year = 1997,
            genre = "Драмы",
            description = "Титаник (Titanic) — американская романтическая драма 1997 года, " +
                    "режиссёр Джеймс Кэмерон, рассказывающая историю любви на борту знаменитого " +
                    "потопленного корабля. Сюжет сосредоточен на молодом художнике Джеке и богатой " +
                    "наследнице Розе, которые влюбляются друг в друга, несмотря на социальные различия."
        ),
        Movie(
            id = 7,
            name = "Миссия невыполнима",
            src = R.drawable.movie8,
            year = 1996,
            genre = "Экшн",
            description = "Миссия невыполнима (Mission: Impossible) — американский шпионский " +
                    "фильм 1996 года, режиссёр Брайан Де Пальма. История рассказывает о спецагенте Итане " +
                    "Ханте, которого преследуют опасные враги после провала очередной миссии."
        ),
        Movie(
            id = 8,
            name = "Пираты Карибского моря: Проклятие Черной жемчужины",
            src = R.drawable.movie9,
            year = 2003,
            genre = "Приключения",
            description = "Пираты Карибского моря: Проклятие Черной жемчужины (Pirates of the Caribbean: The Curse of the Black Pearl) — " +
                    "фэнтезийный приключенческий фильм 2003 года, рассказывающий о капитане Джека Воробье, " +
                    "пытающемся вернуть свою корабль Черная жемчужина и спастись от проклятых пиратов."
        ),
        Movie(
            id = 9,
            name = "Звёздные войны: Эпизод IV – Новая надежда",
            src = R.drawable.movie10,
            year = 1977,
            genre = "Фантастика",
            description = "Звёздные войны: Эпизод IV – Новая надежда (Star Wars: Episode IV – A New Hope) — культовый научно-фантастический " +
                    "фильм Джорджа Лукаса 1977 года, который начался легендарную сагу о противостоянии " +
                    "светлых и темных сил и борьбе за свободу галактики."
        ),
        Movie(
            id = 10,
            name = "Хранители снов",
            src = R.drawable.movie11,
            year = 2012,
            genre = "Мультфильмы",
            description = "Хранители снов (Rise of the Guardians) — анимационный фильм 2012 года, " +
                    "рассказывающий о приключениях команды мифологических существ, включая Санта-Клауса, " +
                    "Зубную Фею, Кролика, Ночного Стража и Лешего, которые защищают детей по всему миру."
        ),
        Movie(
            id = 11,
            name = "Гарри Поттер и философский камень",
            src = R.drawable.movie12,
            year = 2001,
            genre = "Фэнтези",
            description = "Гарри Поттер и философский камень (Harry Potter and the Philosopher's Stone) — первый фильм серии о Гарри Поттере, " +
                    "в котором начинающий волшебник Гарри Поттер отправляется учиться в Хогвартс и узнает о своем истинном происхождении и " +
                    "судьбе."
        ),
        Movie(
            id = 12,
            name = "Джурасик Парк",
            src = R.drawable.movie13,
            year = 1993,
            genre = "Научная фантастика",
            description = "Джурасик Парк (Jurassic Park) — американский научно-фантастический фильм 1993 года, режиссёр Стивен Спилберг. " +
                    "История рассказывает о парке динозавров, где группа ученых сталкивается с последствиями " +
                    "разведения древних ящеров и их неожиданным бегством."
        ),
        Movie(
            id = 13,
            name = "Властелин колец: Возвращение короля",
            src = R.drawable.movie14,
            year = 2003,
            genre = "Фэнтези",
            description = "Властелин колец: Возвращение короля (The Lord of the Rings: The Return of the King) — третий фильм трилогии " +
                    "Властелин колец, который рассказывает о завершении эпической истории борьбы за кольцо власти и " +
                    "решающий момент, когда Фродо и Сэм достигают Мордора."
        ),
        Movie(
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
        )
    )
    val electedMovies
        get() = movies.filter { it.isElected }
    var darkMode by mutableStateOf(false)
    fun movieEvent(event: MovieEvent) {
        when(event) {
            is MovieEvent.BookMarkEvent -> { movies = movies.map {
                if(it.id == event.id)
                    it.copy(isElected = !it.isElected)
                else
                    it
            } }
            is MovieEvent.DarkModeEvent -> {
                darkMode = event.isDarkMode
            }
        }
    }
}

@Composable
fun Application(
    navController: NavController,
    movies: List<Movie>,
    viewModel: MovieViewModel
) {
    Column(
        modifier = Modifier
            .background(if(viewModel.darkMode) Color(26, 26, 26, 255) else Color.Transparent)
            .padding(top = 3.dp)
    ) {
        MovieDisplayer(
            movies = movies,
            navController = navController,
            darkMode = viewModel.darkMode
        )
    }
    Menu(
        offset = 820.dp, // 820 for laptop 810 for my phone 🤔
        darkMode = viewModel.darkMode,
        onFavourite = {
            navController.navigate(route = "Elected")
        },
        onSettings = {
            navController.navigate(route = "Setting")
        },
        onDarkMode = { viewModel.movieEvent(MovieEvent.DarkModeEvent(it)) }
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ApplicationPreview(viewModel: MovieViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Application"
    ) {
        composable(
            route = "Application"
        ) {
            Application(navController = navController, movies = viewModel.movies, viewModel = viewModel)
        }
        composable(
            route = "MovieInfos/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1

            MovieInfos(
                navController = navController,
                id = id,
                movies = viewModel.movies,
                onBookMark = { viewModel.movieEvent(MovieEvent.BookMarkEvent(it)) },
                darkMode = viewModel.darkMode
            )
        }
        composable(
            route = "Elected"
        ) {
            Elected(
                movies = viewModel.electedMovies,
                navController = navController,
                darkMode = viewModel.darkMode
            )
        }
        composable(
            route = "Setting"
        ) {
            Setting(navController = navController, darkMode = viewModel.darkMode)
        }
    }
}