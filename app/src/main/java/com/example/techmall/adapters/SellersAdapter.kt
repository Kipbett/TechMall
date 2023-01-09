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
import com.example.techmall.activities.*
import com.example.techmall.adapters.SellersAdapter.*
import com.example.techmall.models.PhoneDetails
import com.example.techmall.models.SellersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SellersAdapter: RecyclerView.Adapter<SellersAdapter.SellersAdapterViewHolder> {
    var context: Context? = null
    var seller_products:ArrayList<PhoneDetails>? = null
    lateinit var dialog:Dialog
    var fb_user = FirebaseAuth.getInstance()
    lateinit var ds_key:String
    lateinit var user_id:String

    var db_ref = FirebaseDatabase.getInstance().reference

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
                db_ref.child("products").addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        for(ds in p0.children){
                            var img = ds.child("p_imgurl").value.toString()
                             user_id= ds.child("p_uid").value.toString()

                            if (img.equals(products.product_image)){
                                ds_key = ds.key.toString()
                                db_ref.child("products").child(ds_key).removeValue{
                                    error, _ -> {
                                        if (error != null){
                                            Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_LONG).show()
                                            var intent = Intent(context, SellerProductsActivity::class.java)
                                            intent.putExtra("img_url_user", products.product_image)
                                            context!!.startActivity(intent)
                                        } else {
                                            Toast.makeText(context, "Item Not Deleted. \n Try Again Later",
                                                Toast.LENGTH_LONG).show()
                                        }
                                }
                                }
                            }
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(context, "Operation Cancelled\n $p0", Toast.LENGTH_LONG).show()
                    }
                })

                db_ref.child("details").child(fb_user.currentUser!!.uid).addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            var img = ds.child("p_imgurl").value.toString()
                            if (img.equals(products.product_image)) {
                                var ds_key = ds.key.toString()
                                db_ref.child("details").child(fb_user.currentUser!!.uid)
                                    .child(ds_key).removeValue { error, _ ->
                                        {
                                            if (error != null) {
                                                Toast.makeText(
                                                    context,
                                                    "Item Deleted Successfully",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context, "Item Not Deleted. \n Try Again Later",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                            }
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(context, "Database Error! \n $p0", Toast.LENGTH_LONG).show()
                    }
                })

                val intent = Intent(context, SellerProductsActivity::class.java)
                context!!.startActivity(intent)
                dialog.hide()
            })

            review_txt.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Review The Product", Toast.LENGTH_LONG).show()
                if (products.product_category.equals("Smart Phones")){
                    var intent = Intent(context, SmartPhoneActivity::class.java)
                    intent.putExtra("img_url", products.product_image)
                    context!!.startActivity(intent)

                } else if (products.product_category.equals("Computers")){
                    var intent = Intent(context, LaptopActivity::class.java)
                    intent.putExtra("img_url", products.product_image)
                    context!!.startActivity(intent)
                } else{
                    Toast.makeText(context, "Category for the product not available", Toast.LENGTH_LONG).show()
                }
                dialog.hide()
            })

            dialog.show()
        })
    }

    override fun getItemCount(): Int {
        return seller_products!!.size
    }
}