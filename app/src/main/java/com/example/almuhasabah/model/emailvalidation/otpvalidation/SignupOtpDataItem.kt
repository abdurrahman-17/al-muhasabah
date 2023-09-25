package com.example.almuhasabah.model.emailvalidation.otpvalidation

import com.google.gson.annotations.SerializedName

data class SignupOtpDataItem(
    @SerializedName("email")
    val email: String,
)
