package com.special.place.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.special.place.resource.R
import com.special.place.ui.theme.Caption
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Subtitle3

@Composable
fun MyTopAppBar(
    title: String,
    navigationType: String,
    navigationListener: () -> Unit,
    actionType: String,
    actionListener: () -> Unit
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = title, style = Subtitle3, textAlign = TextAlign.Center)
            }

        },
        navigationIcon = {
            when (navigationType) {
                "back" -> {
                    IconButton(onClick = { navigationListener() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null
                        )
                    }
                }
                "close" -> {
                    IconButton(onClick = { navigationListener() }) {
                        Icon(Icons.Filled.Close, "closeIcon")
                    }
                }
            }
        },
        actions = {
            when (actionType) {
                "setting" -> {
                    IconButton(onClick = { actionListener() }) {
                        Icon(Icons.Filled.Settings, "settingIcon")
                    }
                }
                "delete" -> {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable(enabled = true) {
                                actionListener()
                            },
                        text = "삭제요청",
                        style = Caption,
                        color = Grey600
                    )
                }
                else -> {
                    IconButton(onClick = {}) {}
                }
            }

        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}