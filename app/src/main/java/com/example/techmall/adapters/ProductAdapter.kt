package com.example.techmall.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.activities.FragsActivity
import com.example.techmall.activities.ProductReviewActivity
import com.example.techmall.models.ProductModel
import org.w3c.dom.Text

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> {

    var context:Context? = null
    var product_list:ArrayList<ProductModel>? = null

    constructor(context: Context?, product_list: ArrayList<ProductModel>?) : super() {
        this.context = context
        this.product_list = product_list
    }

    class ProductAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var product_image:ImageView = itemView.findViewById(R.id.product_image)
        var product_name:TextView = itemView.findViewById(R.id.product_name)
        var product_initial_price:TextView = itemView.findViewById(R.id.product_price)
        var product_discounted_price:TextView = itemView.findViewById(R.id.product_discounted_price)
        var product_discount:TextView = itemView.findViewById(R.id.product_discount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterVH {
        var view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false)
        return ProductAdapterVH(view)
    }

    override fun onBindViewHolder(holder: ProductAdapterVH, position: Int) {
        var product = product_list!![position]
        holder.product_image.setImageResource(product.product_image!!)
        holder.product_name.text = product.product_name
        holder.product_initial_price.text =product.product_initial_price
        holder.product_discounted_price.text = product.product_discounted_price
        holder.product_discounted_price.paintFlags = holder.product_discounted_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.product_discount.text = product.product_discount_percentage
        holder.itemView.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, FragsActivity::class.java)
            intent.putExtra("category", product.prod_category)
            context!!.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return product_list!!.size
    }
}