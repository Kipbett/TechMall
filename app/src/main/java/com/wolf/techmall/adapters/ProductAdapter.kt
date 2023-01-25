package com.wolf.techmall.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wolf.techmall.R
import com.wolf.techmall.activities.LaptopActivity
import com.wolf.techmall.activities.SmartPhoneActivity
import com.wolf.techmall.models.ProductModel

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> {

    var context:Context? = null
    var product_list:ArrayList<ProductModel>? = null
    var bundle = Bundle()

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

            if (product.prod_category.equals("Smart Phones")){
                var intent = Intent(context, SmartPhoneActivity::class.java)
                intent.putExtra("p_category", product.prod_category)
                intent.putExtra("p_image", product.product_image)
                context!!.startActivity(intent)
            } else {
                var intent = Intent(context, LaptopActivity::class.java)
                intent.putExtra("p_category", product.prod_category)
                intent.putExtra("p_image", product.product_image)
                context!!.startActivity(intent)
            }

        })
    }

    override fun getItemCount(): Int {
        return product_list!!.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    product_list = product_list as ArrayList<ProductModel>
                } else {
                    val resultList = ArrayList<ProductModel>()
                    for (row in product_list!!) {
                        if (row.product_name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    product_list = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = product_list
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                product_list = results?.values as ArrayList<ProductModel>
                notifyDataSetChanged()
            }
        }
    }
}