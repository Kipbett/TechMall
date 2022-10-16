package com.example.techmall.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.activities.Products
import com.example.techmall.models.CategoreisModel

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    var context:Context? = null
    var categories:ArrayList<CategoreisModel>? = null

    constructor(context: Context?, categories: ArrayList<CategoreisModel>?) : super() {
        this.context = context
        this.categories = categories
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image:ImageView = itemView.findViewById(R.id.category_img)
        var category:TextView = itemView.findViewById(R.id.category_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view:View = LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        var category = categories!![position]
        holder.image.setImageResource(category.image!!)
        holder.category.text = category.category_name
        holder.itemView.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, Products::class.java)
            intent.putExtra("category_name", category.category_name)
            context!!.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return categories!!.size
    }
}