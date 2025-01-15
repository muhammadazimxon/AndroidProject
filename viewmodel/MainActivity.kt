package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

data class Person(
    val name: String = "",
    val surname: String = "",
    val age: Int = 0
)

sealed class PersonEvent() {
    class PersonNameEvent(val name: String) : PersonEvent()
    class PersonSurnameEvent(val surname: String) : PersonEvent()
    class PersonAgeEvent(val age: String) : PersonEvent()
}
// extension from data class Person
fun Person.validate()
    = name.length >= 2 && surname.length >= 2 && age in (1..100)

class PersonViewModel : ViewModel() {
    private var _person by mutableStateOf(Person())
    val person
        get() = _person

    fun onChangePerson(event: PersonEvent)
        = when(event) {
            is PersonEvent.PersonNameEvent -> { _person = _person.copy(name = event.name) }
            is PersonEvent.PersonSurnameEvent -> { _person = _person.copy(surname = event.surname) }
            is PersonEvent.PersonAgeEvent -> { _person = _person.copy(age = event.age.toInt()) }
        }
}
@Composable
fun App(viewModel: PersonViewModel = viewModel()) {
    PersonAdd(
        person = viewModel.person,
        handleChangeName = { viewModel.onChangePerson(PersonEvent.PersonNameEvent(it)) },
        handleChangeSurname = { viewModel.onChangePerson(PersonEvent.PersonSurnameEvent(it)) },
        handleChangeAge = { viewModel.onChangePerson(PersonEvent.PersonAgeEvent(it)) }
    )
}
@Composable
fun PersonAdd(
    person: Person,
    handleChangeName: (String) -> Unit,
    handleChangeSurname: (String) -> Unit,
    handleChangeAge: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 25.dp, end = 25.dp)
    ) {
        TextField(
            value = person.name,
            onValueChange = handleChangeName,
            placeholder = { Text(text = "Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = person.surname,
            onValueChange = handleChangeSurname,
            placeholder = { Text(text = "Enter your surname") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = person.age.toString(),
            onValueChange = handleChangeAge,
            placeholder = { Text(text = "Enter your age") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Add",
                textAlign = TextAlign.Center,
                modifier = Modifier.width(100.dp)
            )
        }

        Text(
            text = """
                Имя:     ${person.name}
                Фамилия: ${person.surname}
                Возраст: ${person.age}
            """.trimIndent(),
            fontSize = 22.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App()
}