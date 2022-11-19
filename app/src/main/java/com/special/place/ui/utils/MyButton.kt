package com.special.place.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.ui.theme.*

@Composable
fun PrimaryButton(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple500),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.White)
    }
}

@Composable
fun PrimaryButton(text: String, modifier: Modifier, clickListener: () -> Unit) {
    Button(
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple500),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.White)
    }
}

@Composable
fun PrimaryButtonPressed(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple700),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.White)
    }
}

@Composable
fun PrimaryButtonDisable(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey300),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.White)
    }
}

@Composable
fun SecondaryButton(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey200),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.Black)
    }
}

@Composable
fun SecondaryButtonPressed(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey300),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.Black)
    }
}

@Composable
fun SecondaryButtonSelected(text: String, clickListener: () -> Unit) {
    Button(
        modifier = Modifier
            .width(312.dp)
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple100),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(2.dp, Purple700),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Purple700)
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier, clickListener: () -> Unit) {
    Button(
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Grey200),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() }) {
        Text(text = text, style = Subtitle2, color = Color.Black)
    }
}

@Composable
fun NextButton(buttonName: String, clickListener: () -> Unit, modifier: Modifier) {
    Button(
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Purple500),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = { clickListener() },
        modifier = modifier
            .size(width = 100.dp, height = 56.dp)
    ) {
        Text(text = buttonName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight(700))
        Box(modifier = Modifier.width(4.dp))
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.White)
    }
}