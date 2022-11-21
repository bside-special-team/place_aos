package com.special.place.ui.place.register.hashtags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.special.place.DEFAULT_HASHTAGS
import com.special.place.ui.theme.Grey300
import com.special.place.ui.theme.Grey900
import com.special.place.ui.utils.PrimaryButton
import com.special.place.ui.widget.CenterAlignedTopAppBar
import com.special.place.ui.widget.EmptyChipClickable
import com.special.place.ui.widget.HashtagChipClickable

@Composable
fun HashtagStep(eventListener: HashtagEventListener) {
    val selectedTags: List<String> by eventListener.hashtags.observeAsState(initial = listOf())
//    var editMode by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = "키워드로 장소를 소개해주세요") {

        }
    }) {
        Box {
            Column(modifier = Modifier.fillMaxSize()) {

                if (selectedTags.isEmpty()) {
                    Text(
                        text = "아래 카테고리에서 선택하거나\n이 곳을 눌러 직접 작성해보세요!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
//                                editMode = true;
                            }
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    FlowRow(
                        mainAxisSpacing = 6.dp, crossAxisSpacing = 12.dp, modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        selectedTags.forEach() {
                            HashtagChipClickable(content = it, select = selectedTags.contains(it)) {
                                eventListener.updateHashtag(it)
                            }
                        }
                        EmptyChipClickable {
//                                    editMode = true
                        }
                    }
                }

                Text(
                    text = "${selectedTags.size}/5", modifier = Modifier
                        .padding(top = 14.dp, end = 24.dp)
                        .align(Alignment.End)
                )

                Divider(color = Grey300, modifier = Modifier.padding(top = 20.dp))

                Text(
                    text = "해시태그",
                    color = Grey900,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(start = 24.dp, top = 28.dp)
                )

                FlowRow(
                    mainAxisSpacing = 6.dp, crossAxisSpacing = 12.dp, modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    DEFAULT_HASHTAGS.forEach() {
                        HashtagChipClickable(content = it, select = selectedTags.contains(it)) {
                            eventListener.updateHashtag(it)
                        }
                    }
                }
            }

            PrimaryButton(
                text = "완료", modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                eventListener.next()
            }
        }

    }
}

@Composable
fun InputHashtag() {
    Box() {
        Row() {

        }
    }
}

@Preview
@Composable
fun PreviewHashtagStep() {
    HashtagStep(HashtagEventListener.empty())
}