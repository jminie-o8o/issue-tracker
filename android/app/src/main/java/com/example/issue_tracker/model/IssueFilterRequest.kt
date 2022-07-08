package com.example.issue_tracker.model

data class IssueFilterRequest(
    val isOpened: Boolean? = null,
    val authorId: Int? = null,
    val labelId: Int? = null,
    val milestoneId: Int? = null
)
