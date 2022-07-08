package com.example.issue_tracker.ui.issue

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.common.addElement
import com.example.issue_tracker.common.closeElement
import com.example.issue_tracker.common.removeAllElement
import com.example.issue_tracker.common.removeElement
import com.example.issue_tracker.model.Issue
import com.example.issue_tracker.model.IssueFilterRequest
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository) :
    ViewModel() {

    private val _issueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val issueList: StateFlow<MutableList<Issue>> = _issueList

    private val _closeIssueMessage = MutableSharedFlow<String>()
    val closeIssueMessage: SharedFlow<String> = _closeIssueMessage

    private val _checkedIssueIdList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val checkedIssueIdList: StateFlow<MutableList<Int>> = _checkedIssueIdList

    // 임시 StateFlow
    private val _checkedIssueIdListTemp = MutableStateFlow<List<Int>>(mutableListOf())
    val checkedIssueIdListTemp: StateFlow<List<Int>> = _checkedIssueIdListTemp

    private val _error = MutableSharedFlow<CEHModel>()
    val error: SharedFlow<CEHModel> = _error

    val checkLongClicked = MutableStateFlow<Boolean>(true)

    private val _searchWord = MutableSharedFlow<String>()
    val searchWord = _searchWord.debounce { 400 }

    private val _searchIssueList = MutableStateFlow<List<Issue>>(mutableListOf())
    val searchIssueList: StateFlow<List<Issue>> = _searchIssueList

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(CoroutineException.checkThrowable(throwable))
        }
    }

    fun getIssues() {
        viewModelScope.launch(exceptionHandler) {
            issueRepository.getIssue().collect {
                _issueList.value = it.toMutableList()
                Log.d("Issue", it.toString())
            }
        }
    }

    fun closeIssue(issueId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _issueList.closeElement(issueId)
            val response = issueRepository.closeIssue(issueId)
            when (response.statusCode) {
                200 -> _closeIssueMessage.emit(response.message)
            }
        }
    }

    fun handleSearchWord(word: String) {
        viewModelScope.launch(exceptionHandler) {
            _searchWord.emit(word)
        }
    }

    fun getSearchIssue(word: String) {
        viewModelScope.launch(exceptionHandler) {
            issueRepository.searchIssue(word).collect {
                _searchIssueList.value = it
            }
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