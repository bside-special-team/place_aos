package com.special.place.ui.my

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.special.place.resource.R
import com.special.place.ui.my.act.MyPostData
import com.special.place.ui.my.postlist.PostItem
import com.special.place.ui.my.setting.SettingActivity
import com.special.place.ui.theme.Grey100
import com.special.place.ui.theme.PlaceTheme
import com.special.place.ui.theme.Title1
import com.special.place.ui.utils.MyTopAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyInformationActivity : ComponentActivity() {

    private val vm: MyInformationViewModel by viewModels()

    companion object {
        fun settingNewIntent(context: Context): Intent =
            Intent(context, SettingActivity::class.java)

        fun newIntent(context: Context) = Intent(context, MyInformationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onClose: () -> Unit = {
            finish()
        }
        val onSetting: () -> Unit = {
            startActivity(settingNewIntent(this))
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
                            MyTopAppBar(
                                title = stringResource(id = R.string.top_app_bar_my_information),
                                navigationType = "close",
                                navigationListener = { onClose() },
                                actionType = "setting",
                                actionListener = { onSetting() }
                            )
                        }, content = {

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                items(1) {
                                    MyInformationScreen(vm)
                                    Spacer(modifier = Modifier.height(28.dp))

                                }
                                items(1) {
                                    Divider(
                                        color = Grey100,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(16.dp)
                                    )
                                    Spacer(modifier = Modifier.height(32.dp))
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(23.5.dp),
                                        textAlign = TextAlign.Left,
                                        text = stringResource(id = R.string.txt_current_visited),
                                        style = Title1
                                    )
                                }
                                items(vm.currentVisitedPlace.value!!.size) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 24.dp)
                                    ) {
                                        PostItem(
                                            place = vm.currentVisitedPlace.value!!,
                                            bookmark = vm.isBookmarked,
                                            index = it
                                        )
                                    }
                                }
                            }
                        })

                }
            }
        }

    }
}

