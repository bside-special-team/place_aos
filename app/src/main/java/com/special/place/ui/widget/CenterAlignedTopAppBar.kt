package com.special.place.ui.widget

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.special.place.resource.R

@Composable
fun CenterAlignedTopAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    closeIcon: Boolean = true,
    actions: @Composable RowScope.() -> Unit
) {
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    val context = LocalContext.current

    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {

        //TopAppBar Content
        Box(Modifier.height(32.dp)) {

            //Navigation Icon
            Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        onClick = onBack ?: {
                            (context as? Activity)?.finish() ?: Unit
                        },
                        enabled = true,
                    ) {
                        Icon(
                            painter = painterResource(id = if (closeIcon) R.drawable.ic_close else R.drawable.ic_arrow_right),
                            contentDescription = "Back",
                        )
                    }
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = title
                        )
                    }
                }
            }

            //actions
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}