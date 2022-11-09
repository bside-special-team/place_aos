package com.special.place.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.special.place.ui.theme.*

@Composable
fun CustomDialog(
    value: String, subValue: String, buttonValue:String, subButtonValue:String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(start = 24.dp,top = 36.dp, end = 24.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = value,
                        style = Title1
                    )
                    if(subValue!=""){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = subValue,
                            style = Subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                setShowDialog(false)
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .width(124.dp)
                                .height(56.dp),
                            elevation = elevation(
                                defaultElevation = 0.dp
                            )
                        ) {
                            Text(text = buttonValue,
                                style = Subtitle2,)
                        }
                        Button(
                            onClick = {
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(Purple500),
                            modifier = Modifier
                                .width(124.dp)
                                .height(56.dp)
                        ) {
                            Text(text = subButtonValue,
                            style = Subtitle2,
                            color = Color.White)
                        }
                    }

                }
            }
        }
    }
}