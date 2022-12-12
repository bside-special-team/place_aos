package com.special.place.ui.my.setting.nickname.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.special.domain.repositories.UserRepository
import com.special.place.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NickNameModifyViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel(), NickNameModifyEventListener {
    private val _nickname: MutableLiveData<String> = MutableLiveData()
    override val uiState: MutableLiveData<UiState> = MutableLiveData(UiState.Init)

    init {
        viewModelScope.launch {
            userRepo.currentUser.collectLatest {
                if (it.nickName != null) {
                    _nickname.postValue(it.nickName)
                }
            }
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
            uiState.postValue(UiState.Error(IllegalArgumentException()))
            return
        }
        viewModelScope.launch {
            uiState.postValue(UiState.Progress)

            runCatching {
                userRepo.modifyNickName(nickName)
            }.onSuccess {
                uiState.postValue(UiState.Done)
            }.onFailure {
                uiState.postValue(UiState.Error(it))
            }
        }
    }

    fun hideDialog() {
        uiState.postValue(UiState.Init)
    }


}