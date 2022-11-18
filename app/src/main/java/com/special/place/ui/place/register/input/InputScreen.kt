package com.special.place.ui.place.register.input

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.place.ui.theme.Body1
import com.special.place.ui.theme.Grey300
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Grey900
import com.special.place.ui.utils.NextButton
import com.special.place.ui.widget.CenterAlignedTopAppBar

@Composable
fun InputPlaceNameStep(eventListener: PlaceInputEventListener) {
    val text: String by eventListener.placeName.observeAsState(initial = "")
    var textFieldWidth by remember { mutableStateOf(1.dp) }
    var textFieldColor by remember { mutableStateOf(Grey300) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = "장소의 이름을 지어주세요") {

        }
    }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (textFieldRef, countRef, nextRef) = createRefs()

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .border(
                        BorderStroke(width = textFieldWidth, color = textFieldColor),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .constrainAs(textFieldRef) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(parent.start, margin = 24.dp)
                        end.linkTo(parent.end, margin = 24.dp)

                        width = Dimension.fillToConstraints
                    }
            ) {
                TextField(
                    value = text,
                    onValueChange = { eventListener.setPlaceName(it.take(10)) },
                    placeholder = {
                        Text("두 글자 이상 입력해주세요")
                    },
                    modifier = Modifier
//                        .focusRequester(focusRequester = focusRequester)
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
                    .padding(end = 20.dp)
                    .constrainAs(countRef) {
                        top.linkTo(textFieldRef.top)
                        end.linkTo(textFieldRef.end)
                        bottom.linkTo(textFieldRef.bottom)
                    }
            ) {
                Text(text = text.length.toString() + "/10", style = Body1, color = Grey600)
            }

            NextButton("다음", clickListener = {
                eventListener.next()
            }, modifier = Modifier.constrainAs(nextRef) {
                end.linkTo(parent.end, margin = 24.dp)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            })
        }
    }
}

@Preview
@Composable
fun PreviewPlaceInputNameStep() {
    InputPlaceNameStep(PlaceInputEventListener.empty())
}