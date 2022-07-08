package com.example.issue_tracker.repository

import com.example.issue_tracker.model.SignUpRequest
import com.example.issue_tracker.model.SignUpResponse
import com.example.issue_tracker.network.LoginAPIService
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(private val loginAPIService: LoginAPIService) : SingUpRepository {

    override suspend fun requestSignUp(signUpRequest: SignUpRequest): SignUpResponse {
        return loginAPIService.requestRegister(signUpRequest)
    }
}