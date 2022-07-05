package com.example.issue_tracker.repository

import com.example.issue_tracker.model.Jwt
import com.example.issue_tracker.model.OAuthRequest

interface LoginRepository {

    suspend fun requestGitHubLogin(gitHubOAuthRequest: OAuthRequest): Jwt
}