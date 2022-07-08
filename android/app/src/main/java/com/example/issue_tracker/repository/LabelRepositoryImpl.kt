package com.example.issue_tracker.repository

import com.example.issue_tracker.datasource.DataSource
import com.example.issue_tracker.model.Label
import com.example.issue_tracker.model.LabelDTO
import retrofit2.HttpException
import javax.inject.Inject


class LabelRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
) : LabelRepository {

    override suspend fun addLabel(label: LabelDTO.LabelDTOItem) {
        val result = dataSource.addLabels(label)
        if (!result.isSuccessful) {
            throw HttpException(result)
        }
    }

    override suspend fun getLabelList(): List<Label> =
        dataSource.getLabels().labels.map { labelDTO ->
            Label.of(labelDTO)
        }
}