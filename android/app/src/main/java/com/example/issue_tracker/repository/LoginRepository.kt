package com.example.issue_tracker.repository

import com.example.issue_tracker.model.GitHubOAuthRequest
import com.example.issue_tracker.model.LoginRequest
import com.example.issue_tracker.model.LoginResponse

interface LoginRepository {

    suspend fun requestGitHubLogin(gitHubOAuthRequest: GitHubOAuthRequest): LoginResponse

    suspend fun requestLogin(loginRequest: LoginRequest): LoginResponse
}