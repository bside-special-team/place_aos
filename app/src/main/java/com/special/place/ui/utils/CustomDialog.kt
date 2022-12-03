package com.special.place.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.special.place.ui.theme.*

@Composable
fun CustomDialog(
    title: String,
    message: String? = null,
    primaryButtonText: String,
    secondaryButtonText: String,
    setShowDialog: (Boolean) -> Unit,
    callback: () -> Unit,
    subCallback: (() -> Unit)? = null
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 36.dp,
                        end = 24.dp,
                        bottom = 24.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = title,
                        style = Title1
                    )
                    if (message != null && message != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message,
                            style = Subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                subCallback?.invoke()
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                Grey200
                            ),
                            modifier = Modifier
                                .width(124.dp)
                                .height(56.dp),
                            elevation = elevation(
                                defaultElevation = 0.dp
                            )
                        ) {
                            Text(
                                text = secondaryButtonText,
                                style = Subtitle2,
                            )
                        }
                        Box(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                callback()
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(Purple500),
                            modifier = Modifier
                                .width(124.dp)
                                .height(56.dp)
                        ) {
                            Text(
                                text = primaryButtonText,
                                style = Subtitle2,
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
    }
}