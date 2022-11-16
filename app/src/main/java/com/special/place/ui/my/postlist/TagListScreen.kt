package com.special.place.ui.my.postlist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey800
import com.special.place.ui.theme.Subtitle1


@Composable
fun TagList(list: List<String>) {
    Row(
    ) {
        for (i in 0 until list.size - 1) {
            Box(
                modifier = Modifier
                    .border(1.dp, Grey200, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = list[i] + " ",
                    color = Grey800,
                    style = Subtitle1,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}