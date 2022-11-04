package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.techmall.R
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    lateinit var login_btn:MaterialButton
    lateinit var signup:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn = findViewById(R.id.seller_login)
        signup = findViewById(R.id.sign_up)

        login_btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SellerProductsActivity::class.java)
            startActivity(intent)
        })

        signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        })
    }
}