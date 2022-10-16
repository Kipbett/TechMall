package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.techmall.R

class LoginActivity : AppCompatActivity() {
    lateinit var login_btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn = findViewById(R.id.seller_login)
        login_btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SellerProductsActivity::class.java)
            startActivity(intent)
        })
    }
}