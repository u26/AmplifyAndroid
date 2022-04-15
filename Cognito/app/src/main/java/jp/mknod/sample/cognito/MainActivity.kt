package jp.mknod.sample.cognito

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.auth.AuthUserAttribute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit private var context: Context
    lateinit var edit_username: EditText
    lateinit var edit_password: EditText
    lateinit var edit_email: EditText
    lateinit var bt_register: Button
    lateinit var bt_confirm: Button
    lateinit var bt_signin: Button
    lateinit var edit_code: EditText
    lateinit var bt_FetchUserAttributes: Button


    suspend fun showMsgInCoroutine(msg:String){
        withContext(Dispatchers.Main) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        edit_username = findViewById(R.id.edit_username)
        edit_password = findViewById(R.id.edit_password)
        edit_email = findViewById(R.id.edit_email)
        bt_register = findViewById(R.id.bt_register)

        edit_code = findViewById(R.id.edit_code)
        bt_confirm = findViewById(R.id.bt_confirm)
        bt_signin = findViewById(R.id.bt_signin)
        bt_FetchUserAttributes = findViewById(R.id.bt_FetchUserAttributes)

        // check
        lifecycleScope.launch {

            var ret = Auth.isSignIn()
            Log.i(TAG, "isSignIn $ret")
            showMsgInCoroutine("isSignIn $ret")
        }

        bt_register.setOnClickListener{

            lifecycleScope.launch {

                Log.i(TAG, "START bt_register")
                var ret = Auth.SignUp(
                    edit_username.text.toString(),
                    edit_password.text.toString(),
                    edit_email.text.toString()
                )
                Log.i(TAG, "SignUp $ret")
                Log.i(TAG, "END bt_register")

                showMsgInCoroutine("SignUp $ret")
            }
        }

        bt_confirm.setOnClickListener{

            lifecycleScope.launch {

                Log.i(TAG, "START bt_confirm")
                var ret = Auth.ConfirmSignUp(
                    edit_username.text.toString(),
                    edit_code.text.toString()
                )
                Log.i(TAG, "ConfirmSignUp $ret")
                Log.i(TAG, "END bt_confirm")

                showMsgInCoroutine("ConfirmSignUp $ret")
            }
        }

        bt_signin.setOnClickListener{

            lifecycleScope.launch {

                Log.i(TAG, "START bt_signin")
                var ret = Auth.SignIn(
                    edit_username.text.toString(),
                    edit_password.text.toString()
                )
                Log.i(TAG, "SignIn $ret")
                Log.i(TAG, "END bt_signin")

                showMsgInCoroutine("SignIn $ret")
            }
        }

        bt_FetchUserAttributes.setOnClickListener{

            lifecycleScope.launch {
                Log.i(TAG, "START bt_FetchUserAttributes")
                var attrs = Auth.FetchUserAttributes()
                Log.i(TAG, "$attrs")
                Log.i(TAG, "END bt_FetchUserAttributes")

                for( i in attrs ){
                    var k = i.key.keyString
                    var v = i.value
                    Log.i(TAG, "$k")
                    Log.i(TAG, "$v")
                }
            }
        }
    }
}