package com.special.place.ui.my.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.place.ui.theme.*
import com.special.place.ui.utils.MyTopAppBar
import com.special.place.ui.utils.PrimaryButton

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
    var textFieldWidth by remember { mutableStateOf(1.dp) }
    var textFieldColor by remember { mutableStateOf(Grey300) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ConstraintLayout {
            val (textField, count) = createRefs()
            Box(
                modifier = Modifier
                    .width(312.dp)
                    .height(56.dp)
                    .border(
                        BorderStroke(width = textFieldWidth, color = textFieldColor),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .constrainAs(textField) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                    }
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it.take(6) },
                    modifier = Modifier
                        .focusRequester(focusRequester = focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                textFieldWidth = 3.dp
                                textFieldColor = Grey900
                            } else {
                                textFieldWidth = 1.dp
                                textFieldColor = Grey300
                            }
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .constrainAs(count) {
                        top.linkTo(textField.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = text.length.toString() + "/6", style = Body1, color = Grey600
                )
            }
        }
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
