package com.example.almuhasabah.model.emailvalidation.questionanswerverification

data class QuestionAnswerVerification(
    val questionId: String,
    val userid: String,
    val userresponse: List<Userresponse>
)