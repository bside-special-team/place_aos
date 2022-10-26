package com.special.place.ui.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.special.place.ui.my.act.MyPostData
import com.special.place.ui.my.setting.SettingActivity
import com.special.place.ui.theme.PlaceTheme
import com.special.place.resource.R
import com.special.place.ui.my.postlist.PostItem

class MyInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBack: () -> Unit = {
            finish()
        }
        val myPostList = ArrayList<MyPostData>()
        myPostList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("조용한", "힙한", "신기한"), true))
        myPostList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("시끄러운", "힙한", "신기한"), false))
        myPostList.add(MyPostData("히든플레이스", "가로수길 아래", listOf("얍", "힙한", "신기한"), false))


        setContent {
            PlaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .verticalScroll(scrollState),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            MyTopAppBar(onBack)
                        }, content = {

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 24.dp, vertical = 32.dp)
                            ) {
                                items(1) {
                                    MyInformationScreen()
                                }
                                items(1) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Left,
                                        text = stringResource(id = R.string.txt_current_visited)
                                    )
                                }
                                items(myPostList.size) {
                                    PostItem(list = myPostList, index = it)
                                }
                            }
                        })

                }
            }
        }

    }
}

@Composable
fun MyTopAppBar(onBack: () -> Unit) {
    val ctx = LocalContext.current
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "")
            }

        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        actions = {
            IconButton(onClick = {
                val intent = Intent(ctx, SettingActivity::class.java)
                ContextCompat.startActivity(ctx, intent, null)
            }) {
                Text(text = "설정")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    )
}
