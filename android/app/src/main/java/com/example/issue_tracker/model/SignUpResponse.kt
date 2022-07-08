package com.example.issue_tracker.model


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
)