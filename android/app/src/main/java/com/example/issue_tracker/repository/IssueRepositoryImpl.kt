package com.example.issue_tracker.repository

import com.example.issue_tracker.datasource.DataSource
import com.example.issue_tracker.model.Issue
import com.example.issue_tracker.model.IssueAddRequest
import com.example.issue_tracker.model.IssueCloseResponse
import com.example.issue_tracker.model.IssueFilterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
) : IssueRepository {

    private val _issueFilterRequest = MutableStateFlow(IssueFilterRequest())

    override fun changeIssueFilterRequest(issueFilterRequest: IssueFilterRequest) {
        _issueFilterRequest.value = issueFilterRequest
    }

    override fun getIssue(): Flow<List<Issue>> {
        return dataSource.getIssues(_issueFilterRequest.value).map { issueDTO ->
            issueDTO.issues.map { issueDTOItem ->
                Issue.of(issueDTOItem)
            }
        }
    }

    override suspend fun closeIssue(issueId: Int): IssueCloseResponse {
        return dataSource.closeIssue(issueId)
    }

    override suspend fun addIssue(issueAddRequest: IssueAddRequest): Boolean {
        val result = dataSource.addIssue(issueAddRequest)
        return result.isSuccessful
    }

    override fun searchIssue(word: String): Flow<List<Issue>> {
        return dataSource.searchIssue(word).map { issueDTO ->
            issueDTO.issues.map { issueDTOItem ->
                Issue.of(issueDTOItem)
            }
        }
    }
}