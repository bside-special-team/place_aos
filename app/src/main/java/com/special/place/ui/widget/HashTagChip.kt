package com.special.place.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey800


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