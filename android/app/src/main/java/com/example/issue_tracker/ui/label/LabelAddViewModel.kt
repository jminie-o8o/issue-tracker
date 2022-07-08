package com.example.issue_tracker.ui.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.Label
import com.example.issue_tracker.model.LabelDTO
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.LabelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelAddViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
) : ViewModel() {

    private val _label: MutableStateFlow<Label> = MutableStateFlow(defaultLabel)
    val label: StateFlow<Label> = _label.asStateFlow()

    val labelTitle: MutableStateFlow<String> = MutableStateFlow("")

    val labelDescription: MutableStateFlow<String> = MutableStateFlow("")

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

    fun randomColor() {
        val randomA = 255
        val randomR = (0..255).random()
        val randomG = (0..255).random()
        val randomB = (0..255).random()
        val colorA = convertIntToHex(randomA)
        val colorR = convertIntToHex(randomR)
        val colorG = convertIntToHex(randomG)
        val colorB = convertIntToHex(randomB)
        val randomColorString = buildString {
            append("#")
            append(colorA)
            append(colorR)
            append(colorG)
            append(colorB)
        }
        _label.value = Label(
            labelId = Label.INITIAL_ID,
            labelTitle = labelTitle.value,
            labelContents = labelDescription.value,
            labelColor = randomColorString
        )
    }

    fun saveLabel() {
        viewModelScope.launch(exceptionHandler) {
            if (label.value === defaultLabel) {
                val color = convertColorToLabelDTOColor(Label.INITIAL_COLOR.substring(3))
                labelRepository.addLabel(LabelDTO.LabelDTOItem(
                    id = Label.INITIAL_ID,
                    name = labelTitle.value,
                    description = labelDescription.value,
                    backgroundColor = color))
            } else {
                val item = label.value
                val color = convertColorToLabelDTOColor(item.labelColor.substring(3))
                labelRepository.addLabel(LabelDTO.LabelDTOItem(
                    id = Label.INITIAL_ID,
                    name = item.labelTitle,
                    description = item.labelContents ?: Label.INITIAL_DESCRIPTION,
                    backgroundColor = color
                ))
            }
        }
    }

    private fun convertIntToHex(number: Int) = String.format("%02x", number)

    private fun convertColorToLabelDTOColor(color: String): String = buildString {
        append("0x")
        append(color)
    }

    companion object Companion {
        val defaultLabel = Label(
            Label.INITIAL_ID,
            Label.INITIAL_TITLE,
            Label.INITIAL_DESCRIPTION,
            Label.INITIAL_COLOR)
    }
}