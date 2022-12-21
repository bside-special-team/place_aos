package com.special.place.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.special.place.ui.theme.Purple300
import com.special.place.ui.theme.Purple500

@Composable
fun VisitPlaceProgressBar(progress: Float, startIcon: Int, endIcon: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val iconResource1 =
            painterResource(id = startIcon)
        val iconResource2 =
            painterResource(id = endIcon)
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(28.dp)
                .background(color = Purple500, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp),
                painter = iconResource1, contentDescription = "icon"
            )
        }
        LinearProgressIndicator(
            progress = progress,
            backgroundColor = Color.White,
            color = Purple500,
            modifier = Modifier
                .height(6.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(28.dp)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = iconResource2, contentDescription = "icon")
        }
    }
}

@Composable
fun LandMarkProgressBar(progress: Float, startIcon: Int, endIcon: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val iconResource1 =
            painterResource(id = startIcon)
        val iconResource2 =
            painterResource(id = endIcon)
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(28.dp)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = iconResource1, contentDescription = "icon")
        }
        LinearProgressIndicator(
            progress = progress,
            backgroundColor = Purple300,
            color = Color.White,
            modifier = Modifier
                .height(6.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(28.dp)
                .background(color = Purple300, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = iconResource2, contentDescription = "icon")
        }
    }
}