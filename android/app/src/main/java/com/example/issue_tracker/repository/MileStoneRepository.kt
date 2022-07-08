package com.example.issue_tracker.repository

import com.example.issue_tracker.model.MileStone
import com.example.issue_tracker.model.MileStoneDTO
import kotlinx.coroutines.flow.Flow

interface MileStoneRepository {

    suspend fun addMileStone(item: MileStoneDTO.MileStoneDTOItem)

    suspend fun getMileStoneList(): List<MileStone>
}