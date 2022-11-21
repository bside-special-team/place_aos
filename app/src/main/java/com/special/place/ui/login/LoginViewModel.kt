package com.special.place.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.special.domain.entities.user.LoginStatus
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    // val loginResult: LiveData<LoginStatus> = userRepo.loginStatus.asLiveData()
    val loginStatus: StateFlow<LoginStatus> = userRepo.loginStatus

    init {
        viewModelScope.launch {
            userRepo.loginStatus.collectIndexed { index, value -> Log.d("loginViewModel", "$index :: $value") }
        }

    }

    fun checkLogin() {
        viewModelScope.launch {
            userRepo.checkLogin()
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }
}