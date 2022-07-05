package com.example.issue_tracker.model

data class Jwt(
    val accessToken: String,
) {
    companion object {
        fun fromAccessResponse(jwtDTO: OAuthResponse) = Jwt(
            accessToken = jwtDTO.accessToken.token
        )
    }
}