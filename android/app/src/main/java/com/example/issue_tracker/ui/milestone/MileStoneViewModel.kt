package com.example.issue_tracker.ui.milestone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.MileStone
import com.example.issue_tracker.repository.MileStoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MileStoneViewModel @Inject constructor(
    private val repository: MileStoneRepository,
) : ViewModel() {

    private val _mileStoneList = MutableStateFlow<List<MileStone>>(mutableListOf())
    val mileStoneList = _mileStoneList.asStateFlow()

    init {
        viewModelScope.launch {
            _mileStoneList.value = repository.getMileStoneList()
        }
    }

    fun changeLabelSwiped(index: Int, isSwiped: Boolean) {
        _mileStoneList.value[index].isSwiped = isSwiped
    }

    fun getLabelSwiped(index: Int) = mileStoneList.value[index].isSwiped

    fun changeClickedState() {
        val list = _mileStoneList.value.map {
            it.copy(isLongClicked = !it.isLongClicked)
        }
        _mileStoneList.value = list.toMutableList()
    }
}