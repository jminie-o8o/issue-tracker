package com.example.issue_tracker.repository

import com.example.issue_tracker.model.Issue
import com.example.issue_tracker.model.IssueAddRequest
import com.example.issue_tracker.model.IssueCloseResponse
import com.example.issue_tracker.model.IssueFilterRequest
import kotlinx.coroutines.flow.Flow

interface IssueRepository {

    fun changeIssueFilterRequest(issueFilterRequest: IssueFilterRequest)

    fun getIssue(): Flow<List<Issue>>

    suspend fun closeIssue(issueId: Int): IssueCloseResponse

    suspend fun addIssue(issueAddRequest: IssueAddRequest): Boolean

    fun searchIssue(word: String): Flow<List<Issue>>
}