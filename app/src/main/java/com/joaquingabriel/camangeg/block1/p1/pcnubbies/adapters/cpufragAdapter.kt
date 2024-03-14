package com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters

import Product
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R

class cpufragAdapter(
    private var frag_cpuproducts: MutableList<Product>,
    private val lifecycleOwner: LifecycleOwner,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<cpufragAdapter.MyViewHolder>() {


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
        holder.Ad_price.text = "$" + product.price
        // Assuming product.product_images is a list of image URLs or drawable resource names
        // You'll need to decide how to handle multiple images or select one to display
        val imageUrl = if (product.product_images != null && product.product_images.isNotEmpty()){
            product.product_images.first().image
        } else{
            ""
        }
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image) // Placeholder image
            .error(R.drawable.error_image) // Error image
            .into(holder.Ad_image)

        holder.itemView.setOnClickListener {
            listener.onItemClick(product.id) // Notify the listener about the click
        }
    }

    interface OnItemClickListener {
        fun onItemClick(productId: Int)
    }

    fun updateProducts(newProducts: List<Product>) {
        frag_cpuproducts.clear()
        frag_cpuproducts.addAll(newProducts)
        notifyDataSetChanged()
        Log.d("cpufragAdapter", "Data updated. New size: ${frag_cpuproducts.size}")
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Ad_title: TextView = itemView.findViewById(R.id.profile_name)
        val Ad_price: TextView = itemView.findViewById(R.id.profile_email)
        val Ad_image: ImageView = itemView.findViewById(R.id.profile_img)
    }
}
