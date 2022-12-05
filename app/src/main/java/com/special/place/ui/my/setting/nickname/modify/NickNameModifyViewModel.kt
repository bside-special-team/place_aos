package com.special.place.ui.my.setting.nickname.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.special.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NickNameModifyViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel(), NickNameModifyEventListener {
    private val _nickname: MutableLiveData<String> = MutableLiveData()

    init {
        userRepo.currentUser()?.nickName?.let {
            _nickname.postValue(it)
        }
    }

    override val name: LiveData<String>
        get() = _nickname

    override fun updateNickname(nickName: String) {
        _nickname.value = nickName
    }

    override fun doModify() {
        val nickName = _nickname.value
        if (nickName.isNullOrBlank()) {

            return
        }
        viewModelScope.launch {
            userRepo.modifyNickName(nickName)
        }
    }


}