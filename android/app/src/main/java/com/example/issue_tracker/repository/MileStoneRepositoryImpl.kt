package com.example.issue_tracker.repository

import com.example.issue_tracker.common.addElement
import com.example.issue_tracker.datasource.DataSource
import com.example.issue_tracker.model.MileStone
import com.example.issue_tracker.model.MileStoneDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import javax.inject.Inject

class MileStoneRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
) : MileStoneRepository {

    override suspend fun addMileStone(item: MileStoneDTO.MileStoneDTOItem) {
        val result = dataSource.addMileStones(item)
        if (!result.isSuccessful) {
            throw HttpException(result)
        }
    }

    override suspend fun getMileStoneList() =
        dataSource.getMileStones().milestones.map { mileStoneDTOItem ->
            MileStone.of(mileStoneDTOItem)
        }
}