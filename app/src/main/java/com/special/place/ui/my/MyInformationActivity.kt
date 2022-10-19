package com.special.place.ui.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.special.place.ui.my.setting.SettingActivity
import com.special.place.ui.theme.PlaceTheme

class MyInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBack:() -> Unit ={
            finish()
        }

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
                            MyInformationScreen()
//                            VisitedPlaceScreen()
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
        title = {Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(text = "")
        }

        },
        navigationIcon = {
            IconButton(onClick = {onBack()}) {
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
