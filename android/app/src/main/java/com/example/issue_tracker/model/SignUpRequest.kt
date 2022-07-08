package com.example.issue_tracker.model

data class SignUpRequest(
    val email: String?,
    val password: String?,
    val displayName: String?
)