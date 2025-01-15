package com.example.movieapp.stars

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StarViewModel : ViewModel() {
    var quantity = -1
    val stars
        get() = _stars

    private var _stars by mutableStateOf(listOf(
        Star(id = 0),
        Star(id = 1),
        Star(id = 2),
        Star(id = 3),
        Star(id = 4)
    ))

    fun eventHandler(event: ActionEvent) {
        when(event) {
            is ActionEvent.StarChangeAction -> {
                _stars = _stars.mapIndexed { index, star ->
                    if(index <= event.position)
                        star.copy(isSelected = true)
                    else
                        star.copy(isSelected = false)
                }
                quantity = event.position
            }
        }
    }
}