package com.example.issue_tracker.datasource

import com.example.issue_tracker.model.IssueCloseResponse
import com.example.issue_tracker.model.IssueDTO
import com.example.issue_tracker.model.LabelDTO
import com.example.issue_tracker.model.MileStoneDTO
import com.example.issue_tracker.network.APIService
import retrofit2.Response
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val apiService: APIService,
) : DataSource {
    override suspend fun getIssues(): IssueDTO = apiService.getIssues()

    override suspend fun addLabels(label: LabelDTO.LabelDTOItem) = apiService.addLabels(label)

    override suspend fun addMileStones(mileStone: MileStoneDTO.MileStoneDTOItem) =
        apiService.addMilestones(mileStone)

    override suspend fun getLabels(): LabelDTO = apiService.getLabels()

    override suspend fun getMileStones(): MileStoneDTO = apiService.getMileStones()

    override suspend fun closeIssue(issueId: Int): IssueCloseResponse =
        apiService.closeIssue(issueId)
}