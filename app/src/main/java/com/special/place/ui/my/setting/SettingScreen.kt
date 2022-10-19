package com.special.place.ui.my.setting

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.special.place.resource.R
import androidx.compose.material.Switch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Preview
@Composable
fun SettingScreen() {

    // 임의 데이터
    val nickname = "일상의 발견"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NickNameItem(nickname) // 닉네임
        MyDivider()
        SettingItem(R.string.item_terms_agreement, 0) // 약관 및 동의 관리
        SettingItem(R.string.item_social, R.string.btn_logout) // 소셜 계정 회원
        SettingItem(R.string.item_withdrawal, 0) // 회원 탈퇴
    }
}

@Composable
fun NickNameItem(nickname: String) {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.item_nickname))
            Button(onClick = {
                val intent = Intent(ctx, NicknameModifyActivity::class.java)
                ContextCompat.startActivity(ctx, intent, null)
            }) {
                Text(text = stringResource(id = R.string.btn_modify))
            }
        }
        Text(text = nickname)
    }
}

@Composable
fun SettingItem(item: Int, subItem: Int) {
    val itemText = stringResource(id = item)
    var logoutBtnText: String
    val checkedState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = itemText)
        if (subItem == R.string.btn_logout) {
            logoutBtnText = stringResource(id = subItem)
            Button(onClick = { /*TODO*/ }) {
                Text(text = logoutBtnText)
            }
        }
        if (subItem == 1) {
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
    }

}

@Composable
fun MyDivider() {
    Divider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
}