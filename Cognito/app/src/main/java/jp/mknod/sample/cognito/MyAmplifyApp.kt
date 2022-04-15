package jp.mknod.sample.cognito

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.hub.HubChannel

class MyAmplifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AmplifyAPI.init(applicationContext)
    }
}