package com.special.data.social.google

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.special.data.social.LoginCallback
import com.special.data.social.SocialLogin
import com.special.domain.entities.user.LoginType
import com.special.domain.entities.user.SocialLoginResponse
import com.special.place.resource.R

class GoogleLogin constructor(context: Context, private val callback: LoginCallback) : SocialLogin {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.google_client_id))
        .requestId()
        .requestProfile()
        .requestEmail()
        .build()

    private val client: GoogleSignInClient = GoogleSignIn.getClient(context, gso)


    private val signInContract: ActivityResultContract<Unit, SocialLoginResponse> =
        object : ActivityResultContract<Unit, SocialLoginResponse>() {
            override fun createIntent(context: Context, input: Unit): Intent {
                return client.signInIntent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): SocialLoginResponse {
                return GoogleSignIn.getSignedInAccountFromIntent(intent).run {
                    checkResponse(this)
                }
            }
        }

    private fun checkResponse(task: Task<GoogleSignInAccount>): SocialLoginResponse {
        try {
            val idToken = task.result.idToken
            return if (task.isSuccessful && idToken != null) {
                SocialLoginResponse.success(type = LoginType.Google, idToken = idToken)
            } else {
                SocialLoginResponse.notLogin()
            }
        } catch (e: Exception) {
            return SocialLoginResponse.notLogin()
        }
    }


    private val signInRegistry =
        (context as ActivityResultCaller).registerForActivityResult(signInContract) {
            callback.onResponse(it)
        }

    override fun doLogin() {
        signInRegistry.launch(Unit)
    }

    override fun logout() {
        client.signOut().addOnCompleteListener {
            callback.onResponse(SocialLoginResponse.notLogin())
        }
    }

    override fun checkSigned() {
        client.silentSignIn().addOnCompleteListener {
            callback.onResponse(checkResponse(it))
        }.addOnFailureListener {
            callback.onResponse(SocialLoginResponse.notLogin())
        }
    }


}