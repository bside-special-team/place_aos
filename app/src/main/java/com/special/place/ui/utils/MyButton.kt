package com.special.place.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.special.place.ui.my.MyInformationActivity
import com.special.place.ui.my.MyInformationScreen
import com.special.place.ui.theme.*

@Composable
fun PrimaryButton(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple500),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Color.White)
    }
}
@Composable
fun PrimaryButtonPressed(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple700),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Color.White)
    }
}

@Composable
fun PrimaryButtonDisable(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey300),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Color.White)
    }
}

@Composable
fun SecondaryButton(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey200),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Color.Black)
    }
}
@Composable
fun SecondaryButtonPressed(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey300),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Color.Black)
    }
}
@Composable
fun SecondaryButtonSelected(text:String, clickListener:()->Unit){
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple100),
        border = BorderStroke(2.dp, Purple700),
        onClick = {clickListener()}) {
        Text(text = text, style= Subtitle2, color = Purple700)
    }
}