package com.example.issue_tracker.model

import com.google.gson.annotations.SerializedName

data class IssueDTO(
    @SerializedName("issues")
    val issues: List<IssueDTOItem>,
) {
    data class IssueDTOItem(
        @SerializedName("description")
        val description: String,
        @SerializedName("issueId")
        val issueId: Int,
        @SerializedName("label")
        val label: LabelDTO.LabelDTOItem,
        @SerializedName("milestoneTitle")
        val milestoneTitle: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("isOpened")
        val isOpened: Boolean,
    )
}

data class LabelDTO(
    @SerializedName("labels")
    val labels: List<LabelDTOItem>,
) {
    data class LabelDTOItem(
        @SerializedName("backgroundColor")
        val backgroundColor: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("labelId")
        val id: Int,
        @SerializedName("name")
        val name: String,
    )
}

data class MileStoneDTO(
    @SerializedName("milestones")
    val milestones: List<MileStoneDTOItem>,
) {
    data class MileStoneDTOItem(
        @SerializedName("milestoneId")
        val milestoneId: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("dueDate")
        val dueDate: String?,
        @SerializedName("openedIssuesCount")
        val openedIssuesCount: Int,
        @SerializedName("closedIssuesCount")
        val closedIssuesCount: Int,
    )
}