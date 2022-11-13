package com.example.techmall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.techmall.R
import com.example.techmall.fragments.AddCompFragment
import com.example.techmall.fragments.AddPhoneFragment
import com.example.techmall.fragments.CompEditFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        var category_comp = intent.getStringExtra("add_comp")
        var category_phone = intent.getStringExtra("add_phone")



        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()
        if (intent.getStringExtra("add_comp") == category_comp){
            fragment_transaction.replace(R.id.frags_add, AddCompFragment()).commit()
        } else if (intent.getStringExtra("add_phone") == category_phone)
            fragment_transaction.replace(R.id.frags_add, AddPhoneFragment()).commit()
    }
}