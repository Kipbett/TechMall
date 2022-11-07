package com.example.techmall.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.adapters.ProductAdapter
import com.example.techmall.models.ProductModel

class Products : AppCompatActivity() {
    lateinit var recycler_view:RecyclerView
    lateinit var products_list:ArrayList<ProductModel>
    lateinit var products_adapter:ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        var category = intent.getStringExtra("category")
        supportActionBar!!.title = category
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show()

        recycler_view = findViewById(R.id.products_recycler)

        products_list = ArrayList()
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products_list.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))

        products_adapter = ProductAdapter(this, products_list)

        var layout_manager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layout_manager
        recycler_view.adapter = products_adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.prod_search -> Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
            R.id.prod_cart -> Toast.makeText(this, "Cart", Toast.LENGTH_LONG).show()
            R.id.prod_profile -> Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}