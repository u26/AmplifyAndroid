package jp.mknod.sample.cognito

import android.content.Context

import android.util.Log
import androidx.lifecycle.ViewModel
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AmplifyAPI : ViewModel() {

    private const val TAG = "AmplifyAPI"

    fun init(applicationContext: Context): AmplifyAPI {

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())   // +auth
            Amplify.configure(applicationContext)       // amplify

            Log.i(TAG, "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", error)
        }

        Amplify.Hub.subscribe(HubChannel.AUTH) { event ->
            when (event.name) {
                InitializationStatus.SUCCEEDED.toString() ->
                    Log.i("AuthQuickstart", "Auth successfully initialized")
                InitializationStatus.FAILED.toString() ->
                    Log.i("AuthQuickstart", "Auth failed to succeed")
                else -> when (AuthChannelEventName.valueOf(event.name)) {
                    AuthChannelEventName.SIGNED_IN ->
                        Log.i("AuthQuickstart", "Auth just became signed in")
                    AuthChannelEventName.SIGNED_OUT ->
                        Log.i("AuthQuickstart", "Auth just became signed out")
                    AuthChannelEventName.SESSION_EXPIRED ->
                        Log.i("AuthQuickstart", "Auth session just expired")
                    AuthChannelEventName.USER_DELETED ->
                        Log.i("AuthQuickstart", "User has been deleted")
                    else ->
                        Log.w("AuthQuickstart", "Unhandled Auth Event: ${event.name}")
                }
            }
        }

        return this
    }
}