package com.special.place.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
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
fun EmptyChipClickable() {
    Box(
        modifier = Modifier
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