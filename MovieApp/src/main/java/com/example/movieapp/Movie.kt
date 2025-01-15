package com.example.movieapp

data class Movie(
    val id: Int,
    val src: Int,
    val name: String,
    val genre: String,
    val year: Int,
    val description: String,
    val isElected: Boolean = false
)