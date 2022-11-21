package com.special.place.ui.my.setting

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.special.place.resource.R
import com.special.place.ui.theme.*

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
        SettingItem(
            R.string.item_terms_agreement,
            R.drawable.ic_info_circle,
            R.drawable.ic_arrow_right
        ) // 약관 및 동의 관리
        SettingItem(
            R.string.item_social,
            R.drawable.ic_kakao_login,
            R.string.btn_logout
        ) // 소셜 계정 회원
        SettingItem(R.string.item_withdrawal, R.drawable.ic_siren, 0) // 회원 탈퇴
    }
}

@Composable
fun NickNameItem(nickname: String) {
    val ctx = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp),
        Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "user"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
            ) {
                Text(
                    color = Grey600,
                    text = stringResource(id = R.string.item_nickname),
                    style = Subtitle1
                )
                Text(text = nickname, style = Subtitle4)
            }
        }
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Grey200),
            onClick = {
                val intent = Intent(ctx, NicknameModifyActivity::class.java)
                ContextCompat.startActivity(ctx, intent, null)
            },
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = stringResource(id = R.string.btn_modify),
                style = Body1,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun SettingItem(item: Int, image: Int, subItem: Int) {
    val itemText = stringResource(id = item)
    var logoutBtnText: String

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "user"
            )
            Spacer(modifier = Modifier.width(20.dp))
            if (item == R.string.item_withdrawal) {
                Text(text = itemText, style = Subtitle3, color = Grey600)
            } else {
                Text(text = itemText, style = Subtitle3)
            }

        }
        if (subItem == R.string.btn_logout) {
            logoutBtnText = stringResource(id = subItem)
            Button(
                modifier = Modifier
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(Grey200),
                onClick = { /*todo*/ },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = logoutBtnText, style = Body1, fontWeight = FontWeight.Bold)
            }
        }
        if (subItem == R.drawable.ic_arrow_right) {
            Icon(
                Icons.Filled.KeyboardArrowRight, contentDescription = "arrow",
                tint = Grey600
            )
        }
    }

}

@Composable
fun MyDivider() {
    Divider(
        color = Grey200,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
}