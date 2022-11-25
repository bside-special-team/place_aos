package com.special.place.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.special.domain.entities.user.LoginStatus
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    val loginStatus: Flow<LoginStatus> = userRepo.loginStatus

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

    fun getNickname(): String? {
        return userRepo.currentUser()?.nickName
    }
}