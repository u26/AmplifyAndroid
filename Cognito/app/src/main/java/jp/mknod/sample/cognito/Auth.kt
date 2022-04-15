package jp.mknod.sample.cognito

import android.util.Log
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

object Auth {

    fun isSignIn():Boolean{

        var ret = Amplify.Auth.fetchAuthSession(
            { Log.i("AmplifyQuickstart", "Auth session = $it") },
            { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error) }
        )

        return false
    }

    fun Register(username: String, password: String, email: String){

        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()

        Amplify.Auth.signUp(
            username,
            password,
            options,
            { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
            { Log.e ("AuthQuickStart", "Sign up failed", it) }
        )
    }


    fun Confirm(username: String, code: String) {

        Amplify.Auth.confirmSignUp(
            username,
            code,
            { result ->
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Confirm signUp succeeded")
                } else {
                    Log.i("AuthQuickstart", "Confirm sign up not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
        )
    }
}