package com.example.issue_tracker.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.AppSession
import com.example.issue_tracker.common.addElement
import com.example.issue_tracker.common.removeAllElement
import com.example.issue_tracker.common.removeElement
import com.example.issue_tracker.datastore.CustomDataStore
import com.example.issue_tracker.model.Issue
import com.example.issue_tracker.model.Jwt
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val dataStore: CustomDataStore,
) : ViewModel() {

    private val _issueList = MutableStateFlow<List<Issue>>(mutableListOf())
    val issueList: StateFlow<List<Issue>> = _issueList

    // 임시 StateFlow
    private val _closeIssue = MutableSharedFlow<String>()
    val closeIssue: SharedFlow<String> = _closeIssue

    private val _checkedIssueIdList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val checkedIssueIdList: StateFlow<MutableList<Int>> = _checkedIssueIdList

    // 임시 StateFlow
    private val _checkedIssueIdListTemp = MutableStateFlow<List<Int>>(mutableListOf())
    val checkedIssueIdListTemp: StateFlow<List<Int>> = _checkedIssueIdListTemp

    private val _error = MutableStateFlow(CEHModel(null, ""))
    val error: SharedFlow<CEHModel> = _error.asSharedFlow()

    val checkLongClicked = MutableStateFlow<Boolean>(true)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = CoroutineException.checkThrowable(throwable)
    }

    init {
        getJwt()
        getIssues()
    }

    private fun getJwt() {
        viewModelScope.launch {
            dataStore.getJwt(CustomDataStore.JWT_KEY).collect { jwt ->
                AppSession.jwt = Jwt(jwt)
            }
        }.onJoin
    }

    private fun getIssues() {
        viewModelScope.launch(exceptionHandler) {
            _issueList.value = issueRepository.getIssue()
        }
    }

    // 이슈를 닫는 로직
    // 서버에 issueId 를 보내면 닫히고 남은 이슈 리스트를 가져오는 로직으로 변경 예정
    fun closeIssue(issueId: Int) {
        viewModelScope.launch {
            _closeIssue.emit(issueId.toString())
        }
    }

    // 체크박스를 통해 선택된 issueIdList 닫기를 서버에 전송하는 로직
    fun closeIssueByCheckBox(issueIdList: List<Int>) {
        _checkedIssueIdListTemp.value = issueIdList
    }

    fun changeIssueSwiped(index: Int, isSwiped: Boolean) {
        _issueList.value[index].isSwiped = isSwiped
    }

    fun getIssueSwiped(index: Int): Boolean {
        return _issueList.value[index].isSwiped
    }

    fun changeClickedState() {
        // 내부 값을 바꾸는 것이 아니라 issue 객체까지도 새로 만들어야 한다.
        // 아예 새로 만든 객체를 setValue 해주어 StateFlow 가 notify 할 수 있도록 한다.
        _issueList.update { origin ->
            origin.map { issue ->
                issue.copy(isLongClicked = !issue.isLongClicked) // LongClicked 값만 변경 후 깊은 복사 수행하여 새로운 객체 만들기
            }.toMutableList()
        }
        checkLongClicked.value = !checkLongClicked.value
    }

    fun addChecked(issueId: Int) {
        _checkedIssueIdList.addElement(issueId)
    }

    fun removeChecked(issueId: Int) {
        _checkedIssueIdList.removeElement(issueId)
    }

    fun clearCheckedIdList() {
        _checkedIssueIdList.removeAllElement()
    }
}