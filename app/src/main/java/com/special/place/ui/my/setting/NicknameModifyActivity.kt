package com.special.place.ui.my.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.special.place.ui.theme.Grey300
import com.special.place.ui.theme.PlaceTheme
import com.special.place.ui.utils.MyTopAppBar
import com.special.place.ui.utils.PrimaryButton

var isTextFieldFocused = false

class NicknameModifyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBack: () -> Unit = {
            finish()
        }
        setContent {
            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Scaffold(
                        topBar = {
                            MyTopAppBar(
                                title = stringResource(com.special.place.resource.R.string.top_app_bar_my_nickname_modify),
                                navigationType = "back",
                                navigationListener = { onBack() },
                                actionType = "",
                                actionListener = {}
                            )
                        }, content = {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                            }
                            NicknameModifyScreen()
                        })


                }
            }
        }
    }

}

@Preview
@Composable
fun NicknameModifyScreen() {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("일상의 발견") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .width(312.dp)
                .height(56.dp)
                .border(
                    BorderStroke(width = 1.dp, color = Grey300),
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        PrimaryButton(text = stringResource(com.special.place.resource.R.string.btn_modify_done)) {}
    }
}

// 전체화면의 터치 이벤트 감지
fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}
