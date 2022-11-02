package com.special.place.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.special.place.ui.my.MyInformationActivity
import com.special.place.ui.place.hidden.HiddenPlacePreActivity
import com.special.place.ui.utils.CustomDialog

@Composable
fun MainButtonScreen() {

    val ctx = LocalContext.current
    val showDialog =  remember { mutableStateOf(false) }
    val selectedHidden = remember { mutableStateOf(false) }

    if(showDialog.value)
        CustomDialog(value = "정보가 감춰져있어요", subValue = "직접 방문해야 정보를 열람할 수 있어요.", buttonValue = "방문하기",setShowDialog = {
            showDialog.value = it
        }) {
            Toast.makeText(ctx,it,Toast.LENGTH_SHORT)
        }
    if(selectedHidden.value){
        val intent = Intent(ctx, HiddenPlacePreActivity::class.java)
        startActivity(ctx,intent,null)
    }

    Column(
        // on below line we are adding a modifier to it
        // and setting max size, max height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),

        // on below line we are adding vertical
        // arrangement and horizontal alignment.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val local : String = "영등포"
        val landCnt : Int = 5
        val hiddenCnt : Int = 3
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            Text(text = "${local}동")
            Spacer(modifier = Modifier.width(150.dp))
            ExtendedFloatingActionButton(
                text = { Text(text = "my", textAlign = TextAlign.Center ) },
                onClick = {
                    val intent = Intent(ctx, MyInformationActivity::class.java)
                    startActivity(ctx,intent,null)
                })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(){
            ExtendedFloatingActionButton(
                text = { Text(text = "주변 랜드마크\n $landCnt", textAlign = TextAlign.Center) },
                onClick = {
                    showDialog.value = true
                },
                shape = RectangleShape
            )
            ExtendedFloatingActionButton(
                text = { Text(text = "히든 플레이스 \n $hiddenCnt / 15개", textAlign = TextAlign.Center ) },
                onClick = {
                    selectedHidden.value = true
                },
                shape = RectangleShape
            )
        }

        Spacer(modifier = Modifier.height(400.dp))

        Row(){
            ExtendedFloatingActionButton(
                text = { Text(text = "출발하기") },
                onClick = {
                    Toast.makeText(ctx, "Floating Action Button", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp),
                )
            FloatingActionButton(
                modifier = Modifier
                    .padding(end = 20.dp),
                onClick = { /*TODO*/ }){
                Icon(Icons.Filled.Add, "")
            }
        }

    }
}