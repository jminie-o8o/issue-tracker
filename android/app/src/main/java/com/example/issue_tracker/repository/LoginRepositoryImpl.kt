package com.example.issue_tracker.repository

import com.example.issue_tracker.model.GitHubOAuthRequest
import com.example.issue_tracker.model.LoginRequest
import com.example.issue_tracker.model.LoginResponse
import com.example.issue_tracker.network.LoginAPIService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginAPIService: LoginAPIService): LoginRepository {

    override suspend fun requestGitHubLogin(gitHubOAuthRequest: GitHubOAuthRequest): LoginResponse {
        return loginAPIService.requestGitHubLogin(gitHubOAuthRequest)
    }

    override suspend fun requestLogin(loginRequest: LoginRequest): LoginResponse {
        return loginAPIService.requestLogin(loginRequest)
    }
}