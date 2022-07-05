package com.example.issue_tracker.datasource

import com.example.issue_tracker.model.OAuthRequest
import com.example.issue_tracker.network.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthDataSourceImpl @Inject constructor(
    private val api: AuthApi,
) : OAuthDataSource {

    override suspend fun requestOAuthLogin(gitHubOAuthRequest: OAuthRequest) =
        api.requestGitHubLogin(gitHubOAuthRequest)
}