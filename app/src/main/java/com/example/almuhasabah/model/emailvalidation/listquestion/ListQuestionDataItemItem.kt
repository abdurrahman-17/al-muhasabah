package com.example.almuhasabah.model.emailvalidation.listquestion

data class ListQuestionDataItemItem(
    val Description: String,
    val Options: List<Option>,
    val Question: String,
    val QuestionType: Int,
    val id: Int
)