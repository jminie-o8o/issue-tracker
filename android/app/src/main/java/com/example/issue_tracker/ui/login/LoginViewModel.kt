package com.example.issue_tracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.model.GitHubOAuthRequest
import com.example.issue_tracker.model.LoginRequest
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.LoginRepository
import com.example.issue_tracker.ui.common.MainApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> = _accessToken

    private val _error = MutableStateFlow(CEHModel(null, ""))
    val error: SharedFlow<CEHModel> = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = CoroutineException.checkThrowable(throwable)
    }

    fun requestGitHubLogin(gitHubOAuthRequest: GitHubOAuthRequest) {
        viewModelScope.launch(exceptionHandler) {
            val response = loginRepository.requestGitHubLogin(gitHubOAuthRequest)
            val accessToken =  response.accessToken.token
            _accessToken.value = accessToken

            // SharedPreference 에 accessToken 저장
            MainApplication.prefs.setString("accessToken", accessToken)
        }
    }

    fun requestLogin(loginRequest: LoginRequest) {
        viewModelScope.launch(exceptionHandler) {
            val response = loginRepository.requestLogin(loginRequest)
            val accessToken =  response.accessToken.token
            _accessToken.value = accessToken

            // SharedPreference 에 accessToken 저장
            MainApplication.prefs.setString("accessToken", accessToken)
        }
    }
}