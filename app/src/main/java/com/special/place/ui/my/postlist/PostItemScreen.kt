package com.special.place.ui.my.postlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.special.domain.entities.place.Place
import com.special.place.resource.R
import com.special.place.ui.my.MyInformationViewModel
import com.special.place.ui.theme.Body1
import com.special.place.ui.theme.Subtitle4
import com.special.place.ui.widget.HashtagChip


@Composable
fun PostItem(place: Place, vm: MyInformationViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 23.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "item",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.width(176.dp)
                ) {
                    Text(
                        text = place.placeType.name,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.grey_600),
                        style = Body1
                    )
                    Text(
                        text = place.name,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.grey_900),
                        style = Subtitle4
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = "dots",
                modifier = Modifier.clickable {
                    vm.showPlaceDetail(place)
                }
            )
        }
        FlowRow(
            mainAxisSpacing = 6.dp, crossAxisSpacing = 12.dp,
            modifier = Modifier.padding(start = 64.dp, top = 16.dp)
        ) {
            place.hashTags.forEach {
                HashtagChip(content = it)
            }
        }
    }
}
