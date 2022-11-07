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

        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()

        fragment_transaction.replace(R.id.frags_add, AddPhoneFragment()).commit()
    }
}