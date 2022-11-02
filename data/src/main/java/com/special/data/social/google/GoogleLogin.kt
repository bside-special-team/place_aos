package com.special.data.social.google

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.special.data.social.LoginCallback
import com.special.domain.entities.user.LoginType
import com.special.data.social.SocialLogin
import com.special.domain.entities.user.SocialLoginResponse
import com.special.place.resource.R

class GoogleLogin constructor(context: Context, val callback: LoginCallback) : SocialLogin {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.google_client_id))
        .requestId()
        .requestProfile()
        .requestEmail()
        .build()

    private val client: GoogleSignInClient = GoogleSignIn.getClient(context, gso)


    private val contract: ActivityResultContract<Unit, SocialLoginResponse> =
        object : ActivityResultContract<Unit, SocialLoginResponse>() {
            override fun createIntent(context: Context, input: Unit): Intent {
                return client.signInIntent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): SocialLoginResponse {
                return GoogleSignIn.getSignedInAccountFromIntent(intent).run {
                    val idToken = result.idToken;
                    if (this.isSuccessful && idToken != null) {
                        println("GOOGLE ID TOKEN :: $idToken")

                        SocialLoginResponse.success(type = LoginType.Google, idToken = idToken)
                    } else {
                        SocialLoginResponse.notLogin()
                    }
                }
            }
        }

    private val signInRegistry =
        (context as ActivityResultCaller).registerForActivityResult(contract) {
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
}