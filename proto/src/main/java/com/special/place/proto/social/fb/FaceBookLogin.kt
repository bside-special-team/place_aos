package com.special.place.proto.social.fb

import androidx.activity.result.ActivityResultRegistryOwner
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.special.place.proto.social.LoginCallback
import com.special.place.proto.social.LoginResponse
import com.special.place.proto.social.SocialLogin
import org.json.JSONObject

class FaceBookLogin constructor(private val activity: ActivityResultRegistryOwner, private val callback: LoginCallback) : SocialLogin, FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {
    private val loginManager = LoginManager.getInstance()
    private val callbackManager = CallbackManager.Factory.create()

    init {
        FacebookSdk.setIsDebugEnabled(true)

        loginManager.registerCallback(callbackManager, this)

    }

    override fun doLogin() {
        loginManager.logInWithReadPermissions(activity, callbackManager, listOf("public_profile", "email"))
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
        println("FACEBOOK ID TOKEN :: ${result.authenticationToken?.token}")

        GraphRequest.newMeRequest(result.accessToken, this)
    }

    override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
        runCatching {
            obj!!.toMyResponse()
        }.onSuccess {
            callback.onResponse(it)
        }.onFailure {
            callback.onFailed(it)
        }
    }
}

fun JSONObject.toMyResponse(): LoginResponse = LoginResponse(
    uuid = getString("id"),
    email = getString("name"),
    profileImage = getString("picture"),
    displayName = getString("first_name") + getString("last_name")
)