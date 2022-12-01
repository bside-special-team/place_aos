package com.special.place.ui.place.information

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.domain.entities.place.Place
import com.special.place.resource.R
import com.special.place.ui.UiState
import com.special.place.ui.my.setting.nickname.modify.addFocusCleaner
import com.special.place.ui.place.information.comment.CommentRegisterEventListener
import com.special.place.ui.theme.*
import com.special.place.ui.utils.MyTopAppBar
import com.special.place.ui.utils.PrimaryButton
import com.special.place.ui.utils.PrimaryButtonDisable
import com.special.place.ui.utils.SecondaryButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val PLACE_ID = "id"
const val PLACE_NAME = "name"
const val PLACE_TYPE = "type"
const val PLACE_RECOMMEND_CNT = "recommend"
const val PLACE_VISIT_CNT = "visit"
const val PLACE_WRITER_NAME = "writer"
const val PLACE_IMAGES = "imageList"
const val PLACE_HASH_TAGS = "hashTags"

@AndroidEntryPoint
class PlaceDetailActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context, place: Place? = null): Intent =
            Intent(context, PlaceDetailActivity::class.java).apply {
                if (place != null) {
                    val imageList: java.io.Serializable = place.imageUuids as java.io.Serializable
                    val hashTags: java.io.Serializable = place.hashTags as java.io.Serializable
                    putExtra(PLACE_ID, place.id)
                    putExtra(PLACE_TYPE, place.placeType.name)
                    putExtra(PLACE_NAME, place.name)
                    putExtra(PLACE_RECOMMEND_CNT, place.recommendCount)
                    putExtra(PLACE_VISIT_CNT, place.visitCount)
                    putExtra(PLACE_WRITER_NAME, place.nickName)
                    putExtra(PLACE_IMAGES, imageList)
                    putExtra(PLACE_HASH_TAGS, hashTags)
                }
            }

    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val onClose: () -> Unit = {
            finish()
        }

        val vm: PlaceDetailViewModel by viewModels()

        setContent {
            val commentList by vm.comments.observeAsState(listOf())

            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val bottomSheetState =
                        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

                    var currentBottomSheet: BottomSheetType? by remember {
                        mutableStateOf(BottomSheetType.TYPE1)
                    }
                    val onDelete: () -> Unit = {
                        vm.placeDeleteBtnClick()

                    }
                    vm.setBottomSheetComment.observe(this) {
                        coroutineScope.launch {
                            currentBottomSheet = BottomSheetType.TYPE1
                            bottomSheetState.show()
                        }
                    }
                    vm.setBottomSheetDeletePlace.observe(this) {
                        coroutineScope.launch {
                            currentBottomSheet = BottomSheetType.TYPE2
                            bottomSheetState.show()
                        }
                    }
                    vm.setBottomSheetDeleteComment.observe(this) {
                        coroutineScope.launch {
                            currentBottomSheet = BottomSheetType.TYPE3
                            bottomSheetState.show()
                            Log.d("여기", it)
                        }
                    }
                    Scaffold(
                        topBar = {
                            MyTopAppBar(
                                title = "",
                                navigationType = "close",
                                navigationListener = { onClose() },
                                actionType = "delete",
                                actionListener = { onDelete() }
                            )
                        },
                        content = {
                            ModalBottomSheetLayout(
                                sheetContent = {
                                    currentBottomSheet?.let {
                                        SheetLayout(
                                            bottomSheetType = it,
                                            vm = vm,
                                            closeCallback = {
                                                coroutineScope.launch {
                                                    bottomSheetState.hide()
                                                }
                                            }
                                        )
                                    }
//                                    BottomSheetScreen(vm, bottomSheetContent, screenHeight)
                                },

                                sheetState = bottomSheetState,
                                sheetShape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                            ) {
                                ConstraintLayout {

                                    val (imagePager, info) = createRefs()
                                    val screenWidth =
                                        (LocalConfiguration.current.screenWidthDp - 28).dp

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        items(1) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .constrainAs(imagePager) {
                                                        top.linkTo(parent.top)
                                                        start.linkTo(parent.start)
                                                        end.linkTo(parent.end)
                                                    }
                                            ) {
                                                PlaceImageScreen(vm)
                                                Column() {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(screenWidth)
                                                    )
                                                    Spacer(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(28.dp)
                                                            .background(
                                                                color = Color.White,
                                                                shape = RoundedCornerShape(
                                                                    topStart = 24.dp,
                                                                    topEnd = 24.dp
                                                                )
                                                            )
                                                    )
                                                }

                                            }
                                        }
                                        items(1) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 4.dp)
                                                    .constrainAs(info) {
                                                        start.linkTo(parent.start)
                                                        top.linkTo(imagePager.top)
                                                        end.linkTo(parent.end)
                                                        bottom.linkTo(parent.bottom)
                                                        width = Dimension.fillToConstraints
                                                    }
                                            ) {
                                                PlaceInfoScreen(vm)
                                            }
                                        }
                                        if (commentList.isEmpty()) {
                                            items(1) {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
//                                                    Image(
//                                                        painter = painterResource(R.drawable.ic_empty_comment),
//                                                        contentDescription = null
//                                                    )
                                                    Spacer(modifier = Modifier.height(20.dp))
                                                    Text(text = "작성된 댓글이 없어요", style = Subtitle2)
                                                    Spacer(modifier = Modifier.height(80.dp))
                                                }
                                            }
                                        } else {
                                            items(commentList) { item ->
                                                CommentList(vm, item)
                                                Spacer(modifier = Modifier.height(20.dp))
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SheetLayout(
    bottomSheetType: BottomSheetType,
    vm: PlaceDetailViewModel,
    closeCallback: () -> Unit
) {
    when (bottomSheetType) {
        BottomSheetType.TYPE1 -> CommentBottomSheetScreen(vm, closeCallback) // 댓글 작성
        BottomSheetType.TYPE2 -> DeletePlaceBottomSheetScreen(
            vm,
            closeCallback,
            BottomSheetType.TYPE2
        ) // 장소 삭제 요청
        BottomSheetType.TYPE3 -> DeleteCommentBottomSheetScreen(
            vm,
            closeCallback,
            BottomSheetType.TYPE3
        ) // 댓글 삭제 요청
    }
}

@Composable
fun DeletePlaceBottomSheetScreen(
    vm: PlaceDetailListener,
    closeCallback: () -> Unit,
    type: BottomSheetType
) {
    Column(
        modifier = Modifier
            .padding(vertical = 32.dp, horizontal = 28.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val options = listOf(
            "해당 위치에 없는 게시물이에요",
            "부적절한 내용이 있어요",
            "중복 작성된 게시물이에요"
        )
        Text(text = "이 게시물을 삭제 요청하는 이유", style = Title2, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "3건 이상의 신고가 들어오면 자동 삭제됩니다", style = Subtitle1, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(32.dp))
        DeletePlaceReasonBox(options, vm, closeCallback, type)
    }
}

@Composable
fun DeleteCommentBottomSheetScreen(
    vm: PlaceDetailListener,
    closeCallback: () -> Unit,
    type: BottomSheetType
) {
    Column(
        modifier = Modifier
            .padding(vertical = 32.dp, horizontal = 28.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val options = listOf(
            "개인정보 노출 우려가 있어요",
            "선정적 내용이 있어요",
            "광고성 내용이 있어요"
        )
        Text(text = "이 댓글을 삭제 요청하는 이유", style = Title2, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "3건 이상의 신고가 들어오면 자동 삭제됩니다", style = Subtitle1, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(32.dp))
        DeletePlaceReasonBox(options, vm, closeCallback, type)
    }
}

@Composable
fun DeletePlaceReasonBox(
    options: List<String>,
    vm: PlaceDetailListener,
    closeCallback: () -> Unit,
    type: BottomSheetType
) {

    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        options.forEach { text ->
            Column {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(20.dp),
                            color =
                            if (text == selectedOption) {
                                Purple700
                            } else {
                                Grey200
                            }
                        )
                        .background(
                            shape = RoundedCornerShape(20.dp),
                            color =
                            if (text == selectedOption) {
                                Purple100
                            } else {
                                Grey200

                            }
                        )
                        .clickable {
                            onSelectionChange(text)
                            var idx: Int = 0
                            if (type == BottomSheetType.TYPE2) {
                                when (text) {
                                    "해당 위치에 없는 게시물이에요" -> idx = 0
                                    "부적절한 내용이 있어요" -> idx = 1
                                    "중복 작성된 게시물이에요" -> idx = 2
                                }
                                // Todo 플레이스 삭제 요청 이유
                                // vm.pickPlaceDeleteReason(idx)
                            } else {
                                when (text) {
                                    "개인정보 노출 우려가 있어요" -> idx = 0
                                    "선정적 내용이 있어요" -> idx = 1
                                    "광고성 내용이 있어요" -> idx = 2
                                }
                                // Todo 댓글 삭제 요청 이유
                            }

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = Subtitle2, color =
                        if (text == selectedOption) {
                            Purple700
                        } else {
                            Color.Black
                        },
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            SecondaryButton(
                text = "닫기",
                modifier = Modifier.weight(1f)
            ) {
                closeCallback.invoke()
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            if (selectedOption != "") {
                PrimaryButton(
                    text = "삭제 요청하기",
                    clickListener = {
                        if (type == BottomSheetType.TYPE2) {
                            vm.placeDeleteRequestClick()
                        } else {
                            // todo 댓글삭제 요청 버튼 클릭
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            } else {
                PrimaryButtonDisable(
                    text = "삭제 요청하기",
                    clickListener = {},
                    modifier = Modifier.weight(1f)
                )

            }

        }
    }
}

@Composable
fun CommentBottomSheetScreen(
    eventListener: CommentRegisterEventListener,
    closeCallback: () -> Unit
) {
    val uiState: UiState by eventListener.commentResult.observeAsState(initial = UiState.Init)
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    var textFieldWidth by remember { mutableStateOf(1.dp) }
    var textFieldColor by remember { mutableStateOf(Grey300) }

    if (uiState == UiState.Done) {
        closeCallback.invoke()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .addFocusCleaner(focusManager)
            .wrapContentHeight()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                modifier = Modifier.clickable(onClick = closeCallback)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "댓글을 작성해주세요",
                textAlign = TextAlign.Center,
                style = Subtitle1,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        ConstraintLayout {
            val (textField, count) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp)
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
                    onValueChange = { text = it.take(100) },
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
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .padding(top = 140.dp)
                    .padding(horizontal = 20.dp)
                    .constrainAs(count) {
                        top.linkTo(textField.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = text.length.toString() + "/100", style = Body1, color = Grey600
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "작성 완료",
            isNotProgress = uiState != UiState.Progress,
            modifier = Modifier.fillMaxWidth()
        ) {
            eventListener.registerComment(text)
        }
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

enum class BottomSheetType {
    TYPE1, TYPE2, TYPE3
}