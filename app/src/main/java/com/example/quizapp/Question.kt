package com.example.quizapp

data class Question (
    val id: Int,
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: String
    )