package com.example.issue_tracker.model


import com.google.gson.annotations.SerializedName

data class IssueCloseResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
)