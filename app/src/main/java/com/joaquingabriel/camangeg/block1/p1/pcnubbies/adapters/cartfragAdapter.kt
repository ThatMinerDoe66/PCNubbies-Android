package com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartProduct
import com.bumptech.glide.Glide // Make sure to import Glide

class cartfragAdapter(private var cart_list: MutableList<CartProduct>) :
    RecyclerView.Adapter<cartfragAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cart_list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cartProduct = cart_list[position]
        holder.Ad_name.text = cartProduct.title
        holder.Ad_price.text = cartProduct.price
        // Assuming cartProduct.product_images is a list of image URLs or drawable resource names
        // You'll need to decide how to handle multiple images or select one to display
        val imageUrl = if (cartProduct.product_images != null && cartProduct.product_images.isNotEmpty()) {
            cartProduct.product_images.first().image
        } else {
            ""
        }
        // Load the image using Glide
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.Ad_image)
        // Display brand and unit if available
        holder.Ad_unit.text = cartProduct.title //apparently the Model of the product

        //holder.Ad_quantity.text = cartProduct.quantity.toString() for quantity later
    }

    fun updateProducts(newProducts: List<CartProduct>) {
        cart_list.clear()
        cart_list.addAll(newProducts)
        notifyDataSetChanged()
        Log.d("cpufragAdapter", "Data updated. New size: ${cart_list.size}")
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Ad_name: TextView = itemView.findViewById(R.id.itemBrand)
        val Ad_price: TextView = itemView.findViewById(R.id.itemPrice)
        val Ad_image: ImageView = itemView.findViewById(R.id.itemImg)
        val Ad_unit: TextView = itemView.findViewById(R.id.itemUnit) //apparently the Model of the product
        //val Ad_quantity: TextView = itemView.findViewById(R.id.itemQuantity) // Make sure to add this view in your layout
    }
}
