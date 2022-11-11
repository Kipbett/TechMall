package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.techmall.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var login_btn:Button
    lateinit var signup:TextView
    lateinit var user_email:EditText
    lateinit var user_password:EditText

    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn = findViewById(R.id.seller_login)
        signup = findViewById(R.id.sign_up)
        user_email = findViewById(R.id.login_email)
        user_password = findViewById(R.id.password_login)

        login_btn.setOnClickListener(View.OnClickListener {
            var email = user_email.text.toString()
            var password = user_password.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show()
            else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        var intent = Intent(this, SellerProductsActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()

        var usr = auth.currentUser
        if (usr != null){
            var intent = Intent(this, SellerProductsActivity::class.java)
            startActivity(intent)
        }
    }
}