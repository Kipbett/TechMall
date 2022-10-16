package com.example.techmall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.techmall.R
import com.example.techmall.fragments.CompEditFragment
import com.example.techmall.fragments.Fridge

class FragsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frags)

        supportActionBar!!.title = intent.getStringExtra("category")
        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()
        fragment_transaction.replace(R.id.frags_layout, Fridge()).commit()
    }
}