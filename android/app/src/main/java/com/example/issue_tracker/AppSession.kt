package com.example.issue_tracker

import com.example.issue_tracker.model.Jwt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSession @Inject constructor() {
    companion object {
        var jwt: Jwt? = null
    }
}