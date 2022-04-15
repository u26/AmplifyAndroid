package jp.mknod.sample.cognito

import android.util.Log
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions

// croutine
import com.amplifyframework.kotlin.core.Amplify
//import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object Auth {

    private const val TAG = "Auth"

    suspend fun isSignIn():Boolean = withContext(Dispatchers.IO) {

        var ret = false
        try {
            val session = Amplify.Auth.fetchAuthSession()
            Log.i("AmplifyQuickstart", "Auth session = $session")
            ret = session.isSignedIn

        } catch (error: AuthException) {
            Log.e("AmplifyQuickstart", "Failed to fetch auth session", error)
        }
        return@withContext ret
    }

    suspend fun SignUp( username: String,
                        password: String,
                        email: String):Boolean = withContext(Dispatchers.IO) {
        var ret = false

        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()
        try {
            val result = Amplify.Auth.signUp(username, password, options)
            ret = result.isSignUpComplete
            Log.i(TAG, "Result: $result")

        } catch (error: AuthException) {
            Log.e(TAG, "Sign up failed", error)
        }

        return@withContext ret
    }

    suspend fun ConfirmSignUp( username: String,
                               code: String):Boolean = withContext(Dispatchers.IO) {

        var ret = false
        try {
            val result = Amplify.Auth.confirmSignUp(username, code)
            if (result.isSignUpComplete) {
                Log.i(TAG, "Signup confirmed")
                ret = true
            } else {
                Log.i(TAG, "Signup confirmation not yet complete")
            }
        } catch (error: AuthException) {
            Log.e(TAG, "Failed to confirm signup", error)
        }

        return@withContext ret
    }

    suspend fun SignIn( username: String,
                        password: String ):Boolean = withContext(Dispatchers.IO) {
        var ret = false
        try {
            val result = Amplify.Auth.signIn(username, password)
            if (result.isSignInComplete) {
                ret = true
                Log.i(TAG, "Sign in succeeded")
            } else {
                Log.e(TAG, "Sign in not complete")
            }
        } catch (error: AuthException) {
            Log.e(TAG, "Sign in failed", error)
        }
        return@withContext ret
    }

    suspend fun FetchUserAttributes():List<AuthUserAttribute> = withContext(Dispatchers.IO) {

        var attrs:List<AuthUserAttribute> = listOf()

        try {
            attrs = Amplify.Auth.fetchUserAttributes()
        } catch (error: AuthException) {
            Log.e(TAG, "getUserAttributes failed", error)
        }
        return@withContext attrs
    }

}