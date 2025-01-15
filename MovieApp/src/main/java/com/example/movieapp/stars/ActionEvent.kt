package com.example.movieapp.stars

sealed class ActionEvent() {
    data class StarChangeAction(val position: Int) : ActionEvent()
}