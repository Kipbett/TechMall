package com.example.techmall.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.models.CategoreisModel

class SuggestedAdapter:RecyclerView.Adapter<SuggestedAdapter.SuggestedAdapterVH> {

    var context: Context? = null
    var suggested_items:ArrayList<CategoreisModel>? = null

    constructor(context: Context?, suggested_items: ArrayList<CategoreisModel>?) : super() {
        this.context = context
        this.suggested_items = suggested_items
    }


    class SuggestedAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var suggested_image:ImageView = itemView.findViewById(R.id.category_img)
        var category_txt:TextView = itemView.findViewById(R.id.category_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedAdapterVH {
        var view = LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false)
        return SuggestedAdapterVH(view)
    }

    override fun onBindViewHolder(holder: SuggestedAdapterVH, position: Int) {
        var suggested = suggested_items!![position]
        holder.suggested_image.setImageResource(suggested.image!!)
        holder.category_txt.text = suggested.category_name
    }

    override fun getItemCount(): Int {
        return suggested_items!!.size
    }
}