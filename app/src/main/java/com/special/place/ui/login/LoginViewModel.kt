package com.special.place.ui.login

import androidx.lifecycle.*
import com.special.domain.entities.user.LoginStatus
import com.special.domain.entities.user.SocialLoginResponse
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel(), LoginEventListener {

    val loginResult: LiveData<LoginStatus> = userRepo.loginStatus.asLiveData(viewModelScope.coroutineContext)

    override fun doKakaoLogin() {
        userRepo.kakaoLogin()
    }

    override fun doGoogleLogin() {
        userRepo.googleLogin()
    }
}