package com.special.place.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.place.register.hashtags.HashtagEventListener
import com.special.place.ui.theme.Grey100
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey800
import com.special.place.ui.theme.Grey900

@Composable
fun HashtagChip(content: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .border(1.dp, Grey200, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            content,
            color = Grey800,
            fontSize = 13.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun HashtagChipClickable(content: String, select: Boolean, clickListener: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = clickListener)
            .background(color = if (select) Grey900 else Grey100, shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                content,
                color = if (select) Color.White else Grey800,
                fontSize = 14.sp,
                modifier = Modifier
            )
            Box(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = if (select) R.drawable.ic_x else R.drawable.ic_plus),
                contentDescription = null,
                tint = if (select) Color.White else Grey800
            )
        }
    }
}

@Composable
fun EmptyChipClickable(clickListener: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = clickListener)
            .background(color = Grey100, shape = RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
        }
    }
}

@Composable
fun InputHashtag(eventListener: HashtagEventListener) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(
        modifier = Modifier
            .background(color = Grey900, shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it.take(12)
                },
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (text.isNotEmpty()) {
                        eventListener.updateHashtag(text)
                    }
                    eventListener.updateEditMode(false)
                }),
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .defaultMinSize(minWidth = 10.dp)
                    .padding(end = 6.dp)
                    .focusRequester(focusRequester),
                cursorBrush = SolidColor(Color.White)
            )
            Text(
                text = "${text.length}/12",
                color = Color.White,
                fontSize = 12.sp,
            )
            Icon(
                painter = painterResource(R.drawable.ic_x),
                modifier = Modifier
                    .padding(start = 6.dp)
                    .clickable { eventListener.updateEditMode(false) },
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}