package com.example.issue_tracker.repository

import com.example.issue_tracker.datasource.OAuthDataSource
import com.example.issue_tracker.model.Jwt
import com.example.issue_tracker.model.OAuthRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val oAuthDataSource: OAuthDataSource,
) : LoginRepository {

    override suspend fun requestGitHubLogin(gitHubOAuthRequest: OAuthRequest) =
        Jwt.fromAccessResponse(oAuthDataSource.requestOAuthLogin(gitHubOAuthRequest))
}