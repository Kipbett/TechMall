package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.adapters.SellersAdapter
import com.example.techmall.models.SellersModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SellerProductsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var extended_button: ExtendedFloatingActionButton
    lateinit var fab_add_comp:FloatingActionButton
    lateinit var fab_add_phone:FloatingActionButton
    lateinit var add_comp_txt: TextView
    lateinit var add_phone_txt: TextView
    lateinit var product_list:ArrayList<SellersModel>
    lateinit var adapter: SellersAdapter

    var is_fab_open:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_products)

        extended_button = findViewById(R.id.extended_floating_button)
        fab_add_comp = findViewById(R.id.fab_add_comp)
        fab_add_phone = findViewById(R.id.fab_add_phone)
        add_comp_txt = findViewById(R.id.add_com_txt)
        add_phone_txt = findViewById(R.id.add_phone_txt)

        fab_add_comp.visibility = View.GONE
        fab_add_phone.visibility = View.GONE
        add_comp_txt.visibility = View.GONE
        add_phone_txt.visibility = View.GONE

        is_fab_open = false

        extended_button.shrink()

        extended_button.setOnClickListener(View.OnClickListener {
            if (!is_fab_open){
                fab_add_comp.show()
                fab_add_phone.show()
                add_comp_txt.visibility = View.VISIBLE
                add_phone_txt.visibility = View.VISIBLE
                extended_button.extend()
                is_fab_open = true
            } else {
                fab_add_comp.hide()
                fab_add_phone.hide()
                add_comp_txt.visibility = View.GONE
                add_phone_txt.visibility = View.GONE
                extended_button.shrink()
                is_fab_open = false
            }
        })
        recyclerView = findViewById(R.id.seller_products)

        fab_add_comp.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Add New Computer", Toast.LENGTH_LONG).show()
            var intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("comp", "Add Computer")
            startActivity(intent)
        })

        fab_add_phone.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Add New Phone", Toast.LENGTH_LONG).show()
            var intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("add_phone", "Add Phone")
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