package com.example.issue_tracker.model

data class OAuthRequest(
    val authCode: String,
    val oauthClientName: String = "GITHUB"
)