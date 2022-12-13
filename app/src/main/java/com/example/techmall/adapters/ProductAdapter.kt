package com.example.techmall.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.example.techmall.activities.FragsActivity
import com.example.techmall.models.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> {

    var context:Context? = null
    var product_list:ArrayList<ProductModel>? = null
    var db_ref = FirebaseDatabase.getInstance().reference
    lateinit var img_url:String

    constructor(context: Context?, product_list: ArrayList<ProductModel>?) : super() {
        this.context = context
        this.product_list = product_list
    }

    class ProductAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var product_image:ImageView = itemView.findViewById(R.id.product_image)
        var product_name:TextView = itemView.findViewById(R.id.product_name)
        var product_initial_price:TextView = itemView.findViewById(R.id.product_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterVH {
        var view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false)
        return ProductAdapterVH(view)
    }

    override fun onBindViewHolder(holder: ProductAdapterVH, position: Int) {
        var product = product_list!![position]
        Glide.with(context!!).load(product.product_image).into(holder.product_image)
        //holder.product_image.setImageResource(product.product_image!!)
        holder.product_name.text = product.product_name
        holder.product_initial_price.text =product.product_initial_price
        holder.itemView.setOnClickListener(View.OnClickListener {
            db_ref.child("products").addValueEventListener(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    for(ds in p0.children){
                        img_url = ds.child("p_imgurl").value.toString()
                        if (img_url.equals(product.product_image)){
                            var intent = Intent(context, FragsActivity::class.java)
                            intent.putExtra("category", product.prod_category)
                            intent.putExtra("img_url", img_url)
                            context!!.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Image Url Not Found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })

        })
    }

    override fun getItemCount(): Int {
        return product_list!!.size
    }
}