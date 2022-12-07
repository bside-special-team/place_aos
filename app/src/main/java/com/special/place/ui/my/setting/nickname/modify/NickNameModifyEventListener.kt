package com.special.place.ui.my.setting.nickname.modify

import androidx.lifecycle.LiveData
import com.special.place.ui.UiState

interface NickNameModifyEventListener {
    val uiState: LiveData<UiState>
    val name: LiveData<String>
    fun updateNickname(nickName: String)
    fun doModify()
}