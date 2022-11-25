package com.special.place.ui.place.information

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.domain.entities.place.Place
import com.special.place.resource.R
import com.special.place.ui.my.setting.nickname.modify.addFocusCleaner
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
const val PLACE_RECOMMEND_CNT = "3"
const val PLACE_VISIT_CNT = "3"
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
        val id = intent.getStringExtra(PLACE_ID) ?: ""
        val name = intent.getStringExtra(PLACE_NAME) ?: ""
        val type = intent.getStringExtra(PLACE_TYPE) ?: ""
        val recommendCnt = intent.getIntExtra(PLACE_RECOMMEND_CNT, 0)
        val visitCnt = intent.getIntExtra(PLACE_VISIT_CNT, 0)
        val writerName = intent.getStringExtra(PLACE_WRITER_NAME) ?: ""
        val imageList = intent.getSerializableExtra(PLACE_IMAGES) as List<*>
        val hashTags = intent.getSerializableExtra(PLACE_HASH_TAGS) as List<*>
        vm.setPlaceInfo(
            PlaceInfo(
                id,
                name,
                type,
                recommendCnt,
                visitCnt,
                writerName,
                imageList,
                hashTags
            )
        )

        setContent {
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
                        currentBottomSheet = BottomSheetType.TYPE2
                    }
                    vm.setBottomSheetComment.observe(this) {
                        coroutineScope.launch {
                            currentBottomSheet = BottomSheetType.TYPE1
                            bottomSheetState.show()
                        }
                    }
                    vm.setBottomSheetDelete.observe(this) {
                        coroutineScope.launch {
                            bottomSheetState.show()
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
                                            vm = vm
                                        )
                                    }
//                                    BottomSheetScreen(vm, bottomSheetContent, screenHeight)
                                },
                                sheetState = bottomSheetState,
                                sheetShape = RoundedCornerShape(36.dp)
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
                                                PlaceImageScreen(vm, imageList)
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
                                        items(3) {
                                            CommentScreen(vm)
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
    vm: PlaceDetailViewModel
) {
    when (bottomSheetType) {
        BottomSheetType.TYPE1 -> CommentBottomSheetScreen(vm)
        BottomSheetType.TYPE2 -> DeletePlaceBottomSheetScreen(vm)
    }
}

@Composable
fun DeletePlaceBottomSheetScreen(vm: PlaceDetailViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 32.dp, horizontal = 28.dp)
    ) {
        // TODO 삭제요청 컨텐츠

        Text(text = "이 게시물을 삭제 요청하는 이유", style = Title2, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "3건 이상의 신고가 들어오면 자동 삭제됩니다", style = Subtitle1, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(color = Grey200, shape = RoundedCornerShape(20.dp))
                .clickable { vm.pickPlaceDeleteReason(0) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "해당 위치에 없는 게시물이에요",
                style = Subtitle2, color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(color = Grey200, shape = RoundedCornerShape(20.dp))
                .clickable { vm.pickPlaceDeleteReason(1) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "부적절한 내용이 있어요",
                style = Subtitle2, color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(color = Grey200, shape = RoundedCornerShape(20.dp))
                .clickable { vm.pickPlaceDeleteReason(2) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "중복 작성된 게시물이에요",
                style = Subtitle2, color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            SecondaryButton(
                text = "닫기", clickListener = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            PrimaryButtonDisable(
                text = "삭제 요청하기",
                clickListener = { vm.placeDeleteRequestClick() },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CommentBottomSheetScreen(vm: PlaceDetailViewModel) {

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    var textFieldWidth by remember { mutableStateOf(1.dp) }
    var textFieldColor by remember { mutableStateOf(Grey300) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .addFocusCleaner(focusManager)
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = "close")
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

        PrimaryButton(text = "작성 완료", modifier = Modifier.fillMaxWidth()) {}
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
    TYPE1, TYPE2
}