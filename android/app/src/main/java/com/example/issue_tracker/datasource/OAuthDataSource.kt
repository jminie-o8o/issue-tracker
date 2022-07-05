package com.example.issue_tracker.datasource

import com.example.issue_tracker.model.OAuthRequest
import com.example.issue_tracker.model.OAuthResponse

interface OAuthDataSource {

    suspend fun requestOAuthLogin(gitHubOAuthRequest: OAuthRequest): OAuthResponse
}