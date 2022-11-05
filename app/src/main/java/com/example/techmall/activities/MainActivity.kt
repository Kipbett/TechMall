package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.adapters.CategoryAdapter
import com.example.techmall.adapters.OffersAdapter
import com.example.techmall.adapters.ProductAdapter
import com.example.techmall.adapters.SuggestedAdapter
import com.example.techmall.models.CategoreisModel
import com.example.techmall.models.ProductModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var category_txt:TextView
    lateinit var recyclerViewCategory: RecyclerView
    lateinit var offer_txt:TextView
    lateinit var recyclerViewOffer:RecyclerView
    lateinit var suggested_txt:TextView
    lateinit var recyclerViewSuggested:RecyclerView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var nav_view:NavigationView
    lateinit var drawer_layout:DrawerLayout


    lateinit var categories:ArrayList<CategoreisModel>
    lateinit var products:ArrayList<ProductModel>

    lateinit var productAdapter: ProductAdapter
    lateinit var category_adapter: CategoryAdapter
    lateinit var offer_adapter:OffersAdapter
    lateinit var suggested_adapter:SuggestedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var action_bar = supportActionBar

        toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

        var drawer_toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawer_toggle)
        drawer_toggle.syncState()

        category_txt = findViewById(R.id.categories)
        offer_txt = findViewById(R.id.offers_tv)
        suggested_txt = findViewById(R.id.suggested_tv)
        recyclerViewSuggested = findViewById(R.id.suggested_recyclerview)
        recyclerViewCategory = findViewById(R.id.categories_recycler)
        recyclerViewOffer = findViewById(R.id.offers_recycler)


        categories = ArrayList()
        categories.add(CategoreisModel(R.drawable.computer, "Computers"))
        categories.add(CategoreisModel(R.drawable.film, "Cameras"))
        categories.add(CategoreisModel(R.drawable.truck, "Home Appliances"))
        categories.add(CategoreisModel(R.drawable.computer, "Comp Accessories"))
        categories.add(CategoreisModel(R.drawable.film, "Phones & Tablets"))
        categories.add(CategoreisModel(R.drawable.computer, "Home Theatre"))

        products = ArrayList()
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))
        products.add(ProductModel(R.drawable.computer, "Computer", "KES 25000", "KES 35000", "15%", "Computer"))

        category_adapter = CategoryAdapter(this, categories)
        offer_adapter = OffersAdapter(this, categories)
        suggested_adapter = SuggestedAdapter(this, categories)
        productAdapter = ProductAdapter(this, products)

        var layout_manager_category = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = layout_manager_category
        recyclerViewCategory.adapter = category_adapter

        var layout_manager_offers = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewOffer.layoutManager = layout_manager_offers
        recyclerViewOffer.adapter = offer_adapter

        var layout_manager_suggested = GridLayoutManager(this, 2)
        recyclerViewSuggested.layoutManager = layout_manager_suggested
        recyclerViewSuggested.adapter = productAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search_menu -> Toast.makeText(this, "Search Item", Toast.LENGTH_LONG).show()
            R.id.cart_menu -> Toast.makeText(this, "Add Item To Cart", Toast.LENGTH_LONG).show()
            R.id.login_menu -> Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
            R.id.menu_seller -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.computers -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            R.id.gaming_consoles -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            R.id.smart_phones -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            R.id.home_appliances -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            R.id.home_theaters -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            R.id.logout -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            else -> print("No Selection Made")
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.isDrawerOpen(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

