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
import com.example.techmall.models.PhoneDetails
import com.example.techmall.models.SellersModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SellerProductsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var extended_button: ExtendedFloatingActionButton
    lateinit var fab_add_comp:FloatingActionButton
    lateinit var fab_add_phone:FloatingActionButton
    lateinit var add_comp_txt: TextView
    lateinit var add_phone_txt: TextView
    var product_list:ArrayList<PhoneDetails> = ArrayList()
    lateinit var adapter: SellersAdapter

    var is_fab_open:Boolean = false
    var auth = FirebaseAuth.getInstance()
    var db_ref = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_products)

        var user_id = auth.currentUser?.uid
        var img_url = intent.getStringExtra("img_url_user")


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
            var intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("add_comp", "Add Computer")
            startActivity(intent)
        })

        fab_add_phone.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("add_phone", "Add Phone")
            startActivity(intent)
        })

        adapter = SellersAdapter(this, product_list)
        var layout_manager = LinearLayoutManager(this)
        recyclerView.layoutManager = layout_manager
        recyclerView.adapter = adapter

        if (user_id != null){
            db_ref.child("products")
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        product_list.clear()
                        for (data_snapshot in datasnapshot.children){
//                            var id = data_snapshot.key
                            if (user_id.equals(data_snapshot.child("p_uid").value.toString())){
                                val image = data_snapshot.child("p_imgurl").value.toString()
                                val name = "${data_snapshot.child("p_brand").value.toString()} " +
                                        data_snapshot.child("p_model").value.toString()
                                val stock = data_snapshot.child("p_stock").value.toString()
                                val price = data_snapshot.child("p_price").value.toString()

                                val details = PhoneDetails(name, image, stock, price)
                                product_list.add(details)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error: $p0", Toast.LENGTH_SHORT).show()
                    }

                })


        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        var img_url = intent.getStringExtra("img_url")
        var user_id = auth.currentUser?.uid
        if(user_id == null){
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        var img_url = intent.getStringExtra("img_url")
        var user_id = auth.currentUser?.uid
        if (user_id != null){
            db_ref.child("products").addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        product_list.clear()
                        for (data_snapshot in datasnapshot.children){
//                            var id = data_snapshot.key
                            if (img_url.equals(data_snapshot.child("p_imgurl").value.toString())){
                                val image = data_snapshot.child("p_imgurl").value.toString()
                                val name = "${data_snapshot.child("p_brand").value.toString()} " +
                                        data_snapshot.child("p_model").value.toString()
                                val stock = data_snapshot.child("p_stock").value.toString()
                                val price = data_snapshot.child("p_price").value.toString()

                                val details = PhoneDetails(name, image, stock, price)
                                product_list.add(details)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error: $p0", Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}