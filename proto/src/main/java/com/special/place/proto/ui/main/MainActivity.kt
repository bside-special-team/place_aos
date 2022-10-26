package com.special.place.proto.ui.main

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.special.place.BuildConfig
import com.special.place.proto.ui.login.LoginActivity
import com.special.place.proto.ui.place.PlaceActivity
import com.special.place.proto.ui.selectimage.SelectImageActivity
import com.special.place.proto.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaceTheme {
                Surface {
                    Scaffold(
                        topBar = {
                            TopAppBar {
                                Text("일상의 발견")
                            }
                        }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { startActivity(PlaceActivity.newIntent(this@MainActivity)) }) {
                                Text("지도")
                            }

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { startActivity(SelectImageActivity.newIntent(this@MainActivity)) }) {
                                Text("이미지 선택")
                            }

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    startActivity(LoginActivity.newIntent(this@MainActivity))
                                }) {
                                Text("SNS 로그인")
                            }
                        }
                    }

                }
            }
        }
    }
}
