package com.example.issue_tracker.repository

import com.example.issue_tracker.model.SignUpRequest
import com.example.issue_tracker.model.SignUpResponse

interface SingUpRepository {

    suspend fun requestSignUp(signUpRequest: SignUpRequest): SignUpResponse
}