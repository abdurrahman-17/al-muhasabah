package com.example.almuhasabah.model.emailvalidation.getsecondaryprofile

data class GetSecondaryProfile(
    val age: Int,
    val api_token: String,
    val created_at: String,
    val criteria: Int,
    val email: String,
    val gender: String,
    val id: Int,
    val marital_status: String,
    val name: String,
    val otp: Any,
    val parent_id: Int,
    val phone_no: Long,
    val profile: Any,
    val role_id: Int,
    val status: Any,
    val updated_at: String,
    val username: String
)