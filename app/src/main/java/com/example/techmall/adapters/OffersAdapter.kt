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

class OffersAdapter: RecyclerView.Adapter<OffersAdapter.OffersAdapterVH> {

    private var context: Context? = null
    private var categoreis:ArrayList<CategoreisModel>? = null

    constructor(context: Context?, categoreis: ArrayList<CategoreisModel>?) : super() {
        this.context = context
        this.categoreis = categoreis
    }


    class OffersAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.category_img)
        var category: TextView = itemView.findViewById(R.id.category_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersAdapterVH {
        var view:View = LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false)
        return OffersAdapterVH(view)
    }

    override fun onBindViewHolder(holder: OffersAdapterVH, position: Int) {
        var category = categoreis!![position]
        holder.image.setImageResource(category.image!!)
        holder.category.text = category.category_name
    }

    override fun getItemCount(): Int {
        return categoreis!!.size
    }
}