package com.special.place.proto.social.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.special.place.proto.social.LoginCallback
import com.special.place.proto.social.LoginResponse
import com.special.place.proto.social.SocialLogin

class GoogleLogin constructor(context: Context, callback: LoginCallback): SocialLogin {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestId()
        .requestProfile()
        .requestEmail()
        .build()

    private val client: GoogleSignInClient = GoogleSignIn.getClient(context, gso)


    private val contract: ActivityResultContract<Unit, Result<LoginResponse>> = object : ActivityResultContract<Unit, Result<LoginResponse>>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return client.signInIntent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Result<LoginResponse> {
            return if (resultCode == Activity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(intent).runCatching {
                    result.idToken
                    result.toMyResponse()
                }
            } else {
                Result.failure(IllegalStateException())
            }

        }
    }

    private val signInRegistry = (context as ActivityResultCaller).registerForActivityResult(contract) {
        if (it.isSuccess) {
            callback.onResponse(it.getOrThrow())
        } else {
            callback.onFailed(it.exceptionOrNull() ?: IllegalStateException())
        }
    }

    override fun doLogin() {
        signInRegistry.launch(Unit)
    }

    override fun logout() {
        client.signOut()
    }
}

fun GoogleSignInAccount.toMyResponse(): LoginResponse = LoginResponse(
    uuid = id ?: "",
    email = email,
    displayName = displayName,
    profileImage = photoUrl.toString()
)