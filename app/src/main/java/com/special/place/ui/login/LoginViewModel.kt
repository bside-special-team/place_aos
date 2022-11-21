package com.special.place.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.special.domain.entities.user.LoginStatus
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    private val _loginStatus: MutableLiveData<LoginStatus> = MutableLiveData()
    val loginResult: LiveData<LoginStatus> = _loginStatus

    init {
        viewModelScope.launch {
            userRepo.loginStatus.collectLatest {
                Log.d("loginVM", "isLogin ${it.isLogin}")

                _loginStatus.postValue(it)
            }
        }
    }
}