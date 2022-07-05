package com.example.issue_tracker.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.datastore.CustomDataStore
import com.example.issue_tracker.model.OAuthRequest
import com.example.issue_tracker.network.CEHModel
import com.example.issue_tracker.network.CoroutineException
import com.example.issue_tracker.repository.LoginRepository
import com.example.issue_tracker.ui.HomeActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStore: CustomDataStore,
) : ViewModel() {

    private val _error = MutableStateFlow(CEHModel(null, ""))
    val error: SharedFlow<CEHModel> = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = CoroutineException.checkThrowable(throwable)
    }

    fun requestGitHubLogin(context: Context, gitHubOAuthRequest: OAuthRequest) {
        viewModelScope.launch {
            val jwt =
                withContext(Dispatchers.Default) {
                    loginRepository.requestGitHubLogin(gitHubOAuthRequest)
                }
            dataStore.saveJwt(CustomDataStore.JWT_KEY, jwt.accessToken)

            goToHomeActivity(context)
        }
    }

    private fun goToHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(context, intent, null)
    }

    companion object Companion {
        const val INITIAL_JWT = ""
    }
}