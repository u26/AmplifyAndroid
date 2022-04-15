package jp.mknod.sample.cognito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.amplifyframework.core.Amplify

class MainActivity : AppCompatActivity() {

    lateinit var edit_username: EditText
    lateinit var edit_password: EditText
    lateinit var edit_email: EditText
    lateinit var bt_register: Button
    lateinit var bt_confirm: Button
    lateinit var edit_code: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_username = findViewById(R.id.edit_username)
        edit_password = findViewById(R.id.edit_password)
        edit_email = findViewById(R.id.edit_email)
        bt_register = findViewById(R.id.bt_register)

        edit_code = findViewById(R.id.edit_code)
        bt_confirm = findViewById(R.id.bt_confirm)

        bt_register.setOnClickListener{
            Auth.Register(
                edit_username.text.toString(),
                edit_password.text.toString(),
                edit_email.text.toString()
            )
        }

        bt_confirm.setOnClickListener{
            Auth.Confirm(
                edit_username.text.toString(),
                edit_code.text.toString()
            )
        }

    }
}