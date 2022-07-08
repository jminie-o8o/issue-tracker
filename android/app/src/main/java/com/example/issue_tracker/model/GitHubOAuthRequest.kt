package com.example.issue_tracker.model

data class GitHubOAuthRequest(
    val authCode: String,
    val oauthClientName: String = "GITHUB"
)
