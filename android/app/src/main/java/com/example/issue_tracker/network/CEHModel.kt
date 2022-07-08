package com.example.issue_tracker.network

data class CEHModel(
    val throwable: Throwable?,
    val errorMessage: String?,
) {
    companion object {
        const val INITIAL_MESSAGE = ""
    }
}