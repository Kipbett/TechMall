package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.adapters.SellersAdapter
import com.example.techmall.models.SellersModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SellerProductsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var add_button:FloatingActionButton
    lateinit var product_list:ArrayList<SellersModel>
    lateinit var adapter: SellersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_products)

        recyclerView = findViewById(R.id.seller_products)
        add_button = findViewById(R.id.add_button)

        add_button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Add Item", Toast.LENGTH_LONG).show()
            var intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        })

        product_list = ArrayList()
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.film, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.film, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.film, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.film, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.film, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))
        product_list.add(SellersModel(R.drawable.truck, "Infinix Hot 7", "KES 20000", "Stock: 30"))

        adapter = SellersAdapter(this, product_list)
        var layout_manager = LinearLayoutManager(this)
        recyclerView.layoutManager = layout_manager
        recyclerView.adapter = adapter

    }
}