package com.example.techmall.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.example.techmall.activities.EditActivity
import com.example.techmall.activities.FragsActivity
import com.example.techmall.adapters.SellersAdapter.*
import com.example.techmall.models.PhoneDetails
import com.example.techmall.models.SellersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SellersAdapter: RecyclerView.Adapter<SellersAdapter.SellersAdapterViewHolder> {
    var context: Context? = null
    var seller_products:ArrayList<PhoneDetails>? = null
    lateinit var dialog:Dialog
    lateinit var fb_user:FirebaseUser

    constructor(context: Context?, seller_products: ArrayList<PhoneDetails>?) : super() {
        this.context = context
        this.seller_products = seller_products
    }


    class SellersAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var seller_image:ImageView = itemView.findViewById(R.id.seller_img)
        var prod_name:TextView = itemView.findViewById(R.id.seller_product)
        var prod_price:TextView = itemView.findViewById(R.id.seller_price)
        var prod_stock:TextView = itemView.findViewById(R.id.seller_stock)
        var seller_edit:ImageView = itemView.findViewById(R.id.seller_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellersAdapterViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.sellers_list, parent, false)
        var view_holder = SellersAdapterViewHolder(view)
        dialog = Dialog(context!!)
        dialog.setContentView(R.layout.edit_dialog)
        return view_holder
    }

    override fun onBindViewHolder(holder: SellersAdapterViewHolder, position: Int) {
        fb_user = FirebaseAuth.getInstance().currentUser!!
        var products = seller_products!![position]
        Glide.with(context!!).load(products.product_image).into(holder.seller_image)
        //holder.seller_image.setImageResource(products.p_image!!)
        holder.prod_name.text = products.product_name
        holder.prod_price.text = "KSH ${products.product_price}"
        holder.prod_stock.text = "${products.product_stock} PCS"

        holder.seller_edit.setOnClickListener(View.OnClickListener {
            //Toast.makeText(context, "Edit The Product", Toast.LENGTH_LONG).show()

            var edit_txt:TextView = dialog.findViewById(R.id.edit_product)
            var delete_txt:TextView = dialog.findViewById(R.id.delete_product)
            var review_txt:TextView = dialog.findViewById(R.id.review_product)

            edit_txt.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Edit The Product", Toast.LENGTH_LONG).show()
                dialog.hide()
                val intent = Intent(context, EditActivity::class.java)
                context!!.startActivity(intent)
            })

            delete_txt.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Delete The Product", Toast.LENGTH_LONG).show()
                dialog.hide()
            })

            review_txt.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Review The Product", Toast.LENGTH_LONG).show()
                dialog.hide()
            })

            dialog.show()
        })
    }

    override fun getItemCount(): Int {
        return seller_products!!.size
    }
}