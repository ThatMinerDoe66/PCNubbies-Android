package com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters

import Product
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R

class cpufragAdapter(private var frag_cpuproducts: MutableList<Product>) :
    RecyclerView.Adapter<cpufragAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.frag_cpuproducts, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return frag_cpuproducts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = frag_cpuproducts[position]
        holder.Ad_title.text = product.title
        holder.Ad_price.text = product.price
        // Assuming product.product_images is a list of image URLs or drawable resource names
        // You'll need to decide how to handle multiple images or select one to display
        val imageUrl = product.product_images.firstOrNull()?.image ?: ""
        // Load the image using a library like Glide or Picasso, or use a drawable resource if imageUrl is a resource name
        // For example, using Glide:
        // Glide.with(holder.itemView.context).load(imageUrl).into(holder.Ad_image)
    }

    fun updateProducts(newProducts: List<Product>) {
        frag_cpuproducts.clear()
        frag_cpuproducts.addAll(newProducts)
        notifyDataSetChanged()
        Log.d("cpufragAdapter", "Data updated. New size: ${frag_cpuproducts.size}")
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Ad_title: TextView = itemView.findViewById(R.id.frag_name)
        val Ad_price: TextView = itemView.findViewById(R.id.frag_price)
        val Ad_image: ImageView = itemView.findViewById(R.id.frag_img)
    }
}
