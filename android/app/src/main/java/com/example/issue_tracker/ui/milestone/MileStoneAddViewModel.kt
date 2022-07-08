package com.example.issue_tracker.ui.milestone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.common.timeStampToDateString
import com.example.issue_tracker.model.MileStone
import com.example.issue_tracker.model.MileStoneDTO
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.MileStoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MileStoneAddViewModel @Inject constructor(
    private val repository: MileStoneRepository,
) : ViewModel() {

    private val _mileStone = MutableStateFlow(defaultMileStone)
    val mileStone = _mileStone.asStateFlow()

    val mileStoneTitle: MutableStateFlow<String> = MutableStateFlow("")

    val mileStoneDescription: MutableStateFlow<String> = MutableStateFlow("")

    private val _error = MutableSharedFlow<CEHModel>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.tryEmit(CoroutineException.checkThrowable(throwable))
        }
    }

    fun onPickedDate(timeStamp: Long) {
        val timeStampFormatted = timeStampToDateString(timeStamp)
        _mileStone.value =
            MileStone(
                MileStone.INITIAL_COUNTS,
                mileStoneTitle.value,
                mileStoneDescription.value,
                timeStampFormatted
            )
    }

    fun saveData() {
        viewModelScope.launch(exceptionHandler) {
            if (_mileStone.value === defaultMileStone) {
                repository.addMileStone(MileStoneDTO.MileStoneDTOItem(
                    milestoneId = MileStone.INITIAL_COUNTS,
                    title = mileStoneTitle.value,
                    description = mileStoneDescription.value,
                    dueDate = INITIAL_DATE,
                    openedIssuesCount = MileStone.INITIAL_COUNTS,
                    closedIssuesCount = MileStone.INITIAL_COUNTS,
                ))
            } else {
                val item = mileStone.value
                repository.addMileStone(MileStoneDTO.MileStoneDTOItem(
                    milestoneId = MileStone.INITIAL_COUNTS,
                    title = item.title,
                    description = item.description,
                    dueDate = item.dueDate,
                    openedIssuesCount = MileStone.INITIAL_COUNTS,
                    closedIssuesCount = MileStone.INITIAL_COUNTS,
                ))
            }
        }
    }

    companion object {
        private const val INITIAL_DATE = "선택사항 (yyyy-mm-dd)"
        val defaultMileStone =
            MileStone(
                MileStone.INITIAL_COUNTS,
                MileStone.INITIAL_VALUE,
                MileStone.INITIAL_VALUE,
                INITIAL_DATE
            )
    }
}