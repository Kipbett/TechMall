package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.techmall.R
import com.example.techmall.fragments.CompEditFragment

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()
        fragment_transaction.replace(R.id.frags_edit, CompEditFragment()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, SellerProductsActivity::class.java)
        startActivity(intent)
        finish()
    }
}