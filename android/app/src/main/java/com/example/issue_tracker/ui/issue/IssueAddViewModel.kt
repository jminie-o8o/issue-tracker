package com.example.issue_tracker.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.IssueAddRequest
import com.example.issue_tracker.model.Label
import com.example.issue_tracker.model.MileStone
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.IssueRepository
import com.example.issue_tracker.repository.LabelRepository
import com.example.issue_tracker.repository.MileStoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueAddViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    private val mileStoneRepository: MileStoneRepository,
    private val issueRepository: IssueRepository
) : ViewModel() {

    private val _labelList = MutableStateFlow<List<Label>>(mutableListOf())
    val labelList = _labelList.asStateFlow()

    private val _mileStoneList = MutableStateFlow<List<MileStone>>(mutableListOf())
    val mileStoneList = _mileStoneList.asStateFlow()

    private val _labelChoose = MutableStateFlow(defaultLabel)
    val labelChoose = _labelChoose.asStateFlow()

    private val _mileStoneChoose = MutableStateFlow(defaultMileStone)
    val mileStoneChoose = _mileStoneChoose.asStateFlow()

    private val _error = MutableStateFlow(CEHModel(null, ""))
    val error: SharedFlow<CEHModel> = _error.asSharedFlow()

    val isSuccess = MutableStateFlow(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = CoroutineException.checkThrowable(throwable)
    }

    fun addIssue(issueAddRequest: IssueAddRequest) {
        viewModelScope.launch(exceptionHandler) {
            isSuccess.value = issueRepository.addIssue(issueAddRequest)
        }
    }

    fun loadLabelAndMileStone() {
        viewModelScope.launch(exceptionHandler) {
            _labelList.value = labelRepository.getLabelList()
            _mileStoneList.value = mileStoneRepository.getMileStoneList()
        }
    }

    fun findClickedLabelMenu(id: Int) {
        val clickedMenu = labelList.value[id].copy()
        _labelChoose.value = clickedMenu
    }

    fun findClickedMileStoneMenu(id: Int) {
        val clickedMenu = mileStoneList.value[id].copy()
        _mileStoneChoose.value = clickedMenu
    }

    companion object {
        val defaultLabel = Label(
            Label.INITIAL_ID,
            Label.INITIAL_TITLE,
            Label.INITIAL_DESCRIPTION,
            Label.INITIAL_COLOR
        )
        val defaultMileStone =
            MileStone(
                MileStone.INITIAL_COUNTS,
                MileStone.INITIAL_VALUE,
                MileStone.INITIAL_VALUE,
                MileStone.INITIAL_VALUE,
            )
    }
}