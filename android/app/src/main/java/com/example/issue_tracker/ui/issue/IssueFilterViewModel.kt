package com.example.issue_tracker.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.common.addElement
import com.example.issue_tracker.model.IssueFilterRequest
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
class IssueFilterViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val labelRepository: LabelRepository,
    private val mileStoneRepository: MileStoneRepository
) : ViewModel() {

    private val _labelList = MutableStateFlow<List<Label>>(mutableListOf())
    val labelList = _labelList.asStateFlow()

    private val _mileStoneList = MutableStateFlow<List<MileStone>>(mutableListOf())
    val mileStoneList = _mileStoneList.asStateFlow()

    // 사실 LiveData 여도 상관 없는 데다가 flow{...} 처럼 Flow 빌더가 없이 사용하는 것이 괜찮을까?
    private val _statusChoose = MutableStateFlow("열린 이슈")
    val statusChoose = _statusChoose.asStateFlow()

    private val _writerChoose = MutableStateFlow("선택")
    val writerChoose = _writerChoose.asStateFlow()

    private val _labelChoose = MutableStateFlow("선택")
    val labelChoose = _labelChoose.asStateFlow()

    private val _mileStoneChoose = MutableStateFlow("선택")
    val mileStoneChoose = _mileStoneChoose.asStateFlow()

    private val _issueFilterRequest = MutableStateFlow(IssueFilterRequest())
    val issueFilterRequest = _issueFilterRequest.asStateFlow()

    private val _error = MutableStateFlow(CEHModel(null, ""))
    val error: SharedFlow<CEHModel> = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = CoroutineException.checkThrowable(throwable)
    }

    fun loadLabelAndMileStone() {
        viewModelScope.launch(exceptionHandler) {
            _labelList.value = labelRepository.getLabelList()
            _mileStoneList.value = mileStoneRepository.getMileStoneList()
        }
    }

    fun setStatusChoose(choose: String) {
        _statusChoose.value = choose
    }

    fun setLabelChoose(choose: String) {
        _labelChoose.value = choose
    }

    fun setMileStoneChoose(choose: String) {
        _mileStoneChoose.value = choose
    }

    fun setStatusRequest(isOpenedValue: Boolean) {
        val temp = _issueFilterRequest.value.copy(isOpened = isOpenedValue)
        _issueFilterRequest.value = temp
    }

    fun setLabelRequest(labelIdValue: Int) {
        val temp = _issueFilterRequest.value.copy(labelId = labelIdValue)
        _issueFilterRequest.value = temp
    }

    fun setMileStoneRequest(mileStoneIdValue: Int) {
        val temp = _issueFilterRequest.value.copy(milestoneId = mileStoneIdValue)
        _issueFilterRequest.value = temp
    }

    fun setIssueFilterRequest() {
        issueRepository.changeIssueFilterRequest(issueFilterRequest.value)
    }
}