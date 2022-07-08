package com.example.issue_tracker.model

data class IssueAddRequest(
    val labelId: Int,
    val mileStoneId: Int,
    val authorId: Int,
    val assigneeId: Int,
    val title: String,
    val description: String,
    val isOpened: Boolean
    ) {
    companion object {
        const val INITIAL_AUTHOR_ID = 1
        const val INITIAL_ASSIGNEE_ID = 1
        const val INITIAL_IS_OPENED = true
    }
}