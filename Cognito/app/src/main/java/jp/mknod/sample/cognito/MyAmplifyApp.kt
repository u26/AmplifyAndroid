package jp.mknod.sample.cognito

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class MyAmplifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())   // +auth
            Amplify.configure(applicationContext)       // amplify

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }
}