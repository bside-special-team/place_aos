package com.special.place.proto.ui.selectimage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.special.place.proto.toast
import com.special.place.proto.ui.theme.PlaceTheme

class SelectImageActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, SelectImageActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceTheme {
                Surface {
                    SelectImageSkeleton()
                }
            }
        }
    }
}

@Composable
fun SelectImageSkeleton() {
    val context = LocalContext.current
    var imageUriList by remember { mutableStateOf<List<Uri>>(listOf()) }

    val imageSelectorLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUriList = imageUriList.plus(uri)
            } else {
                context.toast("No image selected!")
            }
        }

    val requestStoragePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->

            if (permissionGranted) {
                imageSelectorLauncher.launch("image/*")
            } else {
                context.toast("Please allow storage permission for select image.")
            }
        }

    Scaffold(topBar = {
        TopAppBar {
            Text("이미지를 추가하고 비교하기")
        }
    },
        floatingActionButton = {
            IconButton(onClick = { requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
                Icon(Icons.Filled.Add, "fab")
            }
        }) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(imageUriList) { index, uri ->
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = uri)
                            .build()
                    ),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(Color.White)
                )
            }
        }
    }
}