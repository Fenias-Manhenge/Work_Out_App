package com.example.work_out

data class Exercise(
    val id: Int,
    val name: String,
    val image: Int,
    var isCompleted: Boolean,
    var isSelected: Boolean
)
