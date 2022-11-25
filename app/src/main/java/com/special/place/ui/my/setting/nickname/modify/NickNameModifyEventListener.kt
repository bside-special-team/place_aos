package com.special.place.ui.my.setting.nickname.modify

import androidx.lifecycle.LiveData

interface NickNameModifyEventListener {
    val name: LiveData<String>
    fun updateNickname(nickName: String)
    fun doModify()
}