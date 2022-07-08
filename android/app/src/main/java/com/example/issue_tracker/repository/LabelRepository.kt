package com.example.issue_tracker.repository

import com.example.issue_tracker.model.Label
import com.example.issue_tracker.model.LabelDTO

interface LabelRepository {

    suspend fun addLabel(label: LabelDTO.LabelDTOItem)

    suspend fun getLabelList(): List<Label>
}