package jp.mknod.sample.cognito

import android.content.Context
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.core.plugin.Plugin
import com.amplifyframework.hub.HubChannel

object AmplifyAPI {

    private const val TAG = "AmplifyAPI"

    fun init(applicationContext: Context): AmplifyAPI {

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())   // auth
            Amplify.addPlugin(AWSApiPlugin())           // api
            // Amplify.addPlugin<Plugin<*>>(AWSDataStorePlugin()) // DataStore

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