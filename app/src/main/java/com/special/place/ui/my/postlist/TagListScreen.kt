package com.special.place.ui.my.postlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.special.place.resource.R


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
                text = list[i] + " "
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}