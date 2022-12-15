package com.special.place.ui.my.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.special.place.ui.utils.MyTopAppBar

class PolicyActivity : ComponentActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, PolicyActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface {

                Scaffold(
                    topBar = {
                        MyTopAppBar(
                            title = "개인정보 처리 방침",
                            navigationType = "back",
                            navigationListener = { finish() },
                            actionType = "",
                            actionListener = {}
                        )
                    }, content = {
                        val webViewState = rememberWebViewState(url = "https://rogue1228.notion.site/2fc69e7d28784940878a11bf6030c49f")
                        WebView(state = webViewState,
                            onCreated = {
                                it.settings.run {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    javaScriptCanOpenWindowsAutomatically = true
                                }
                            }
                        )
                    })

            }
        }
    }

}