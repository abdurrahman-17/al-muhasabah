package com.example.almuhasabah.model.emailvalidation.secondaryprofile

import com.google.gson.annotations.SerializedName

data class SecondaryProfileDataItem (
    @SerializedName("age")
    val age: Int,
    @SerializedName("api_token")
    val api_token: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("criteria")
    val criteria: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("marital_status")
    val marital_status: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("otp")
    val otp: Any,
    @SerializedName("parent_id")
    val parent_id: Any,
    @SerializedName("phone_no")
    val phone_no: Long,
    @SerializedName("profile")
    val profile: Any,
    @SerializedName("role_id")
    val role_id: Int,
    @SerializedName("status")
    val status: Any,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("username")
    val username: String

)