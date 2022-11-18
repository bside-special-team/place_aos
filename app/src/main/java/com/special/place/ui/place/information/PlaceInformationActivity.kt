package com.special.place.ui.place.information

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.special.place.ui.theme.PlaceTheme
import com.special.place.ui.utils.MyTopAppBar

class PlaceInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val onClose: () -> Unit = {
            finish()
        }
        val onDelete: () -> Unit = {
            /*todo 삭제요청 버튼*/
        }
        setContent {
            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            MyTopAppBar(
                                title = "",
                                navigationType = "close",
                                navigationListener = { onClose() },
                                actionType = "delete",
                                actionListener = { onDelete() }
                            )
                        }, content = {

                            ConstraintLayout {

                                val (imagePager, info) = createRefs()
                                val screenWidth = (LocalConfiguration.current.screenWidthDp - 28).dp

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
                                            PlaceImageScreen()
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
                                            PlaceInfoScreen()
                                        }
                                    }
                                    items(3) {
                                        CommentScreen()
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

