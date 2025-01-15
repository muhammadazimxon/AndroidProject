package com.example.todoproject.components

import com.example.todoproject.components.taskContainer.Urgency

data class Task(
    val text: String,
    val urgency: Urgency,
    val id: Int,
    var isSelected: Boolean = false
)
