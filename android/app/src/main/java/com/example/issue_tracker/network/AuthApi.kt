package com.example.issue_tracker.network

import com.example.issue_tracker.model.OAuthRequest
import com.example.issue_tracker.model.OAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login/oauth")
    suspend fun requestGitHubLogin(
        @Body gitHubOAuthRequest: OAuthRequest
    ): OAuthResponse
}