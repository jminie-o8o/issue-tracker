package com.example.issue_tracker.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.Issue
import com.example.issue_tracker.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository) : ViewModel() {

    private val _issueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val issueList: StateFlow<MutableList<Issue>> = _issueList

    private val _closeIssue = MutableStateFlow("")
    val closeIssue: StateFlow<String> = _closeIssue

    // 이슈 리스트를 가져오는 함수
    // API 로 가져와 처리하는 로직으로 변경 예정
    fun getIssue() {
        viewModelScope.launch {
            issueRepository.getIssue().collect { issue ->
                _issueList.value = issue
            }
        }
    }
     // 이슈를 닫는 로직
    // 서버에 issueId 를 보내면 닫히고 남은 이슈 리스트를 가져오는 로직으로 변경 예정
    fun closeIssue(issueId: Int){
        _closeIssue.value = issueId.toString()
    }

    fun changeIssueSwiped(index: Int, isSwiped: Boolean) {
        _issueList.value[index].isSwiped = isSwiped
    }

    fun getIssueSwiped(index: Int): Boolean {
        return _issueList.value[index].isSwiped
    }

    fun changeClickedState() {

        // 내부 값을 바꾸는 것이 아니라 issue 객체까지도 새로 만들어야 한다.
        val list = _issueList.value.map {
            it.copy(isLongClicked = !it.isLongClicked) // LongClicked 값만 변경 후 깊은 복사 수행하여 새로운 객체 만들기
        }
        _issueList.value = list.toMutableList() // 아예 새로 만든 객체를 setValue 해주어 StateFlow 가 notify 할 수 있도록 한다.
    }
}