package com.example.issue_tracker.network

import com.example.issue_tracker.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPIService {

    @POST("register")
    suspend fun requestRegister(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST("login/oauth")
    suspend fun requestGitHubLogin(
        @Body gitHubOAuthRequest: GitHubOAuthRequest
    ): LoginResponse

    @POST("login")
    suspend fun requestLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}