package com.special.place.proto.social.fb

import android.app.Activity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.special.place.proto.social.LoginCallback
import com.special.place.proto.social.LoginResponse
import com.special.place.proto.social.SocialLogin
import org.json.JSONObject

class FaceBookLogin constructor(private val activity: Activity, private val callback: LoginCallback) : SocialLogin, FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {
    private val loginManager = LoginManager.getInstance()

    init {
        loginManager.registerCallback(CallbackManager.Factory.create(), this)

    }

    override fun doLogin() {
        loginManager.logIn(activity, listOf("email"))
    }

    override fun logout() {
        loginManager.logOut()
    }

    override fun onCancel() {
        callback.onFailed(IllegalStateException())
    }

    override fun onError(error: FacebookException) {
        callback.onFailed(error)
    }

    override fun onSuccess(result: LoginResult) {
        GraphRequest.newMeRequest(result.accessToken, this)
    }

    override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
        runCatching {
            obj?.toMyResponse()?.let {
                callback.onResponse(it)
            }
        }
    }
}

fun JSONObject.toMyResponse(): LoginResponse = LoginResponse(
    uuid = getString("id"),
    email = getString("name"),
    profileImage = getString("picture"),
    displayName = getString("first_name") + getString("last_name")
)