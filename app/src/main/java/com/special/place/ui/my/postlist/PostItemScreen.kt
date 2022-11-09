package com.special.place.ui.my.postlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.special.place.resource.R
import com.special.place.ui.my.act.MyPostData


@Composable
fun PostItem(list: ArrayList<MyPostData>, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_marker_question),
                contentDescription = "item",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.width(176.dp)
            ) {
                Text(
                    text = list[index].placeType,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.grey_600)
                )
                Text(
                    text = list[index].placeName,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.grey_900)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (list[index].bookmark) {
                // 보라
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_bookmark_purple),
                    contentDescription = "bookmark"
                )
            } else {
                // 회색
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_bookmark_grey),
                    contentDescription = "bookmark"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = "dots"
            )
        }
        Row() {
            Spacer(modifier = Modifier.width(64.dp))
            TagList(list[index].hashTag)
        }

    }
}