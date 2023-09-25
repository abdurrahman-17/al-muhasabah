package com.example.almuhasabah.model.emailvalidation.listquestion

data class Option(
    val category_id: Int,
    val correctanswer: Int,
    val options: String,
    val questionanswer_id: Int
)