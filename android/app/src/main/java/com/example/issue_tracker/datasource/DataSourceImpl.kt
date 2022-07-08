package com.example.issue_tracker.datasource

import com.example.issue_tracker.model.*
import com.example.issue_tracker.network.APIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val apiService: APIService,
) : DataSource {
    override fun getIssues(issueFilterRequest: IssueFilterRequest): Flow<IssueDTO> {
        return flow {
            emit(
                apiService.getIssues(
                    issueFilterRequest.isOpened,
                    issueFilterRequest.authorId,
                    issueFilterRequest.labelId,
                    issueFilterRequest.milestoneId
                )
            )
        }
    }

    override suspend fun addLabels(label: LabelDTO.LabelDTOItem) = apiService.addLabels(label)

    override suspend fun addMileStones(mileStone: MileStoneDTO.MileStoneDTOItem) =
        apiService.addMilestones(mileStone)

    override suspend fun getLabels(): LabelDTO = apiService.getLabels()

    override suspend fun getMileStones(): MileStoneDTO = apiService.getMileStones()

    override suspend fun closeIssue(issueId: Int): IssueCloseResponse =
        apiService.closeIssue(issueId)

    override suspend fun addIssue(issueAddRequest: IssueAddRequest) =
        apiService.addIssue(issueAddRequest)

    override fun searchIssue(word: String): Flow<IssueDTO> {
        return flow {
            emit(apiService.searchIssues(word))
        }
    }
}