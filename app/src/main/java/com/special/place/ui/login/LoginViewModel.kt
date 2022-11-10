package com.special.place.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.special.domain.entities.user.LoginStatus
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    val loginResult: LiveData<LoginStatus> = userRepo.loginStatus.asLiveData(viewModelScope.coroutineContext)
}