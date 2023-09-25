package com.example.almuhasabah.model.emailvalidation.emailvalidation

import com.google.gson.annotations.SerializedName

data class SignUpEmailDataItem(
    @SerializedName("email")
    val email: String,
    @SerializedName("otp")
    val otp: String
)
