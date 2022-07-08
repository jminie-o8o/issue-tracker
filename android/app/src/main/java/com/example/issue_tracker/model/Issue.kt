package com.example.issue_tracker.model

data class Issue(
    val issueId: Int,
    val mileStone: String,
    val title: String,
    val contents: String,
    val label: Label,
    var isSwiped: Boolean = false,
    var isLongClicked: Boolean = false,
    var isChecked: Boolean = false,
) {
    companion object {
        fun of(item: IssueDTO.IssueDTOItem) = Issue(
            issueId = item.issueId,
            mileStone = item.milestoneTitle,
            title = item.title,
            contents = item.description,
            label = Label.of(item.label)
        )
    }
}

data class Label(
    val labelId: Int,
    val labelTitle: String,
    val labelContents: String?,
    val labelColor: String,
    var isSwiped: Boolean = false,
    var isLongClicked: Boolean = false,
) {
    companion object {
        const val INITIAL_ID = 0
        const val INITIAL_COLOR = "#FF828282"
        const val INITIAL_TITLE = ""
        const val INITIAL_DESCRIPTION = "새로운 기능 추가"

        fun of(item: LabelDTO.LabelDTOItem): Label {
            val parsedColorString = item.backgroundColor.substring(2)
            val color = buildString {
                append("#")
                append("FF")
                append(parsedColorString)
            }
            return Label(
                labelId = item.id,
                labelTitle = item.name,
                labelColor = color,
                labelContents = item.description
            )
        }
    }
}

data class MileStone(
    val mileStoneId: Int,
    val title: String,
    val description: String?,
    val dueDate: String?,
    val openedIssueCount: Int = 0,
    val closedIssueCount: Int = 0,
    val progress: String = "",
    var isSwiped: Boolean = false,
    var isLongClicked: Boolean = false,
) {
    companion object {
        const val INITIAL_COUNTS = 0
        const val INITIAL_VALUE = ""

        fun of(item: MileStoneDTO.MileStoneDTOItem) = MileStone(
            mileStoneId = item.milestoneId,
            title = item.title,
            description = item.description,
            dueDate = item.dueDate,
            openedIssueCount = item.openedIssuesCount,
            closedIssueCount = item.closedIssuesCount,
            progress = INITIAL_VALUE
        )
    }
}