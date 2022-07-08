package com.example.issue_tracker.ui.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.Label
import com.example.issue_tracker.repository.LabelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
) : ViewModel() {

    private val _labelList = MutableStateFlow<MutableList<Label>>(mutableListOf())
    val labelList = _labelList.asStateFlow()

    init {
        viewModelScope.launch {
            _labelList.value = labelRepository.getLabelList().toMutableList()
        }
    }

    fun changeLabelSwiped(index: Int, isSwiped: Boolean) {
        _labelList.value[index].isSwiped = isSwiped
    }

    fun getLabelSwiped(index: Int) = labelList.value[index].isSwiped

    fun changeClickedState() {
        val list = _labelList.value.map {
            it.copy(isLongClicked = !it.isLongClicked)
        }
        _labelList.value = list.toMutableList()
    }
}