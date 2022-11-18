package com.special.place.ui.place.register.select.picture

import android.Manifest
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.special.place.resource.R
import com.special.place.ui.theme.Grey200
import com.special.place.ui.theme.Grey600
import com.special.place.ui.theme.Subtitle5
import com.special.place.ui.utils.NextButton
import com.special.place.ui.utils.PrimaryButton
import com.special.place.ui.widget.CenterAlignedTopAppBar

@Composable
fun SelectPictureStep(eventListener: SelectPictureEventListener) {
    val imageList: List<Uri> by eventListener.pictures.observeAsState(initial = listOf())

    val context = LocalContext.current

    val imageSelectorLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                eventListener.selectPicture(uri)
            }
        }

    val requestStoragePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->

            if (permissionGranted) {
                imageSelectorLauncher.launch("image/*")
            }
        }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = "장소의 사진을 선택해주세요") {
            IconButton(onClick = { requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (imageList.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = "사진을 추가해주세요", style = Subtitle5, color = Grey600)
                    Box(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "사진 추가", modifier = Modifier.width(108.dp)) {
                        requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 3),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(imageList) { uri ->
                        val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))

                        Box(modifier = Modifier.aspectRatio(1.0F)) {
                            AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .background(color = Grey200, shape = CircleShape)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clickable {
                                            eventListener.unselectPicture(uri)
                                        }
                                        .align(Alignment.Center)
                                        .size(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            NextButton(
                buttonName = "다음 ${imageList.size}/5", clickListener = {
                    eventListener.next()
                }, modifier = Modifier
                    .padding(bottom = 24.dp, end = 24.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSelectPictureStep() {
    SelectPictureStep(SelectPictureEventListener.empty())
}
