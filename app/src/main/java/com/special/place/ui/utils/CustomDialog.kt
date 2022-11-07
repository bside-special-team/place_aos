package com.special.place.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    value: String, subValue: String, buttonValue:String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    val txtField = remember { mutableStateOf(value) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = value,
                        style = TextStyle(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = subValue,
                        style = TextStyle(
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                setValue(txtField.value)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = buttonValue)
                        }

                    }
                    Box(modifier = Modifier.padding(40.dp, 10.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                setValue(txtField.value)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "아니요")
                        }

                    }
                }
            }
        }
    }
}