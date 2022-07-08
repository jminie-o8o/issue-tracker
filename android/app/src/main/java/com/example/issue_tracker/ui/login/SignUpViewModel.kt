package com.example.issue_tracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.SignUpRequest
import com.example.issue_tracker.model.SignUpResponse
import com.example.issue_tracker.repository.SingUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val singUpRepository: SingUpRepository) : ViewModel() {
    private val _signUpResponse = MutableSharedFlow<SignUpResponse>()
    val signUpResponse: SharedFlow<SignUpResponse> = _signUpResponse

    fun requestSignUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            val response = singUpRepository.requestSignUp(signUpRequest)
            _signUpResponse.emit(response)
        }
    }
}