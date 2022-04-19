package jp.mknod.sample.cognito

import android.util.Log
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.auth.options.AuthSignUpOptions

// croutine
import com.amplifyframework.kotlin.core.Amplify
//import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object Auth {

    private const val TAG = "Auth"

    suspend fun isSignIn(): Boolean = withContext(Dispatchers.IO) {

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

    suspend fun SignUp(
        username: String,
        password: String,
        email: String
    ): Boolean = withContext(Dispatchers.IO) {
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

    suspend fun ConfirmSignUp(
        username: String,
        code: String
    ): Boolean = withContext(Dispatchers.IO) {

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

    suspend fun SignIn(
        username: String,
        password: String
    ): Boolean = withContext(Dispatchers.IO) {
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

    suspend fun FetchUserAttributes(): List<AuthUserAttribute> = withContext(Dispatchers.IO) {

        var attrs: List<AuthUserAttribute> = listOf()

        try {
            attrs = Amplify.Auth.fetchUserAttributes()
        } catch (error: AuthException) {
            Log.e(TAG, "getUserAttributes failed", error)
        }
        return@withContext attrs
    }

    suspend fun UpdateUerAttribute(email: String): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        val attribute = AuthUserAttribute(AuthUserAttributeKey.email(), email)
        try {
            val result = Amplify.Auth.updateUserAttribute(attribute)
            Log.i("AuthDemo", "Updated user attribute = $result")
            ret = true
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to update user attribute.", error)
        }
        return@withContext ret
    }

    suspend fun ResetPassword(username: String): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        try {
            val result = Amplify.Auth.resetPassword(username)
            Log.i("AuthQuickstart", "Password reset OK: $result")
            ret = true
        } catch (error: AuthException) {
            Log.e("AuthQuickstart", "Password reset failed", error)
        }
        return@withContext ret
    }

    suspend fun ConfirmResetPassword(new_password: String, code: String): Boolean =
        withContext(Dispatchers.IO) {

            var ret = false
            try {
                Amplify.Auth.confirmResetPassword(new_password, code)
                Log.i("AuthQuickstart", "New password confirmed")
                ret = true
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Failed to confirm password reset", error)
            }
            return@withContext ret
        }

    suspend fun UpdatePassword(old_passeord: String, new_passeord: String): Boolean =
        withContext(Dispatchers.IO) {

            var ret = false
            try {
                Amplify.Auth.updatePassword(old_passeord, new_passeord)
                Log.i("AuthQuickstart", "Updated password successfully")
                ret = true
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Password update failed", error)
            }
            return@withContext ret
        }

    suspend fun SignOut(username: String, global_sign_out: Boolean = false): Boolean =
        withContext(Dispatchers.IO) {

            var ret = false
            val options = AuthSignOutOptions.builder()
                .globalSignOut(true)
                .build()

            try {
                if (global_sign_out) {
                    // Calling signOut without any options will just delete the local cache and keychain of the user.
                    // If you would like to sign out of all devices, invoke the signOut api with advanced options.
                    Amplify.Auth.signOut(options)
                    Log.i("AuthQuickstart", "Signed out globally")
                } else {
                    Amplify.Auth.signOut()
                    Log.i("AuthQuickstart", "Signed out successfully")
                }
                ret = true

            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign out failed", error)
            }
            return@withContext ret
        }

    suspend fun func_sample(username: String): Boolean = withContext(Dispatchers.IO) {

        var ret = false

        return@withContext ret
    }

}