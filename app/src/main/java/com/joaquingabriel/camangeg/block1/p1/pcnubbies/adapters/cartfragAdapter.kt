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
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient.productImagesMap

class cartfragAdapter(private var cart_list: MutableList<CartProduct>,
                      private val listener: OnProductRemoveListener) :
    RecyclerView.Adapter<cartfragAdapter.MyViewHolder>() {

    // Define the interface
    interface OnProductRemoveListener {
        fun onProductRemove(productId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cart_list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cartProduct = cart_list[position]
        Log.d("cartfragAdapter", "Product: ${cartProduct.title}, Cart Quantity: ${cartProduct.cartQuantity}")
        holder.Ad_name.text = cartProduct.title
        holder.Ad_price.text = "$ " + cartProduct.price
        holder.Ad_quantity.text = cartProduct.cartQuantity.toString()

        // Use the productImagesMap to find the image URL for the cart item
        val imageUrl = productImagesMap[cartProduct.id.toString()] ?: ""
        Log.d("cartfragAdapter", "Image URL: $imageUrl")

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.Ad_image)

        // Display brand and unit if available
        holder.Ad_unit.text = cartProduct.title

        //Remove item from cart
        holder.itemView.findViewById<ImageView>(R.id.removeProduct).setOnClickListener {
            val productId = cart_list[position].id
            listener.onProductRemove(productId)
        }
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
        val Ad_quantity: TextView = itemView.findViewById(R.id.itemQuantity) // Make sure to add this view in your layout
    }
}
