package com.special.place.ui.my.postlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.theme.Grey800
import com.special.place.ui.theme.Subtitle1


@Composable
fun TagList(list: List<String>) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        for (i in 0 until list.size - 1) {
            Text(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.grey_100),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(top = 6.dp, bottom = 6.dp, start = 8.dp, end = 8.dp),
                text = list[i] + " ",
                color = Grey800,
                style = Subtitle1,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}