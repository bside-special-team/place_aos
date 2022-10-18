package com.special.place.proto.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.special.place.proto.ui.theme.PlaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    private val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.initLogin(this)

        setContent {
            PlaceTheme {
                Surface {
                    LoginSkeleton(vm = vm)
                }
            }
        }
    }
}

@Composable
fun LoginSkeleton(vm: LoginViewModel) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.kakaoLogin()
            }) {
                Text("KAKAO Login")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.facebookLogin()
            }) {
                Text("FACEBOOK Login")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.googleLogin()
            }) {
                Text("GOOGLE Login")
            }

            Spacer(modifier = Modifier.size(width = 10.dp, height = 32.dp))

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.kakaoLogout()
            }) {
                Text("KAKAO Logout")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.facebookLogout()
            }) {
                Text("FACEBOOK Logout")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                vm.googleLogout()
            }) {
                Text("GOOGLE Logout")
            }
        }
    }


}
