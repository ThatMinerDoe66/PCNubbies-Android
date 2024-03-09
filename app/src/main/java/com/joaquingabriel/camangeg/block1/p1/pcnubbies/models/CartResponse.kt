package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

data class CartResponse(
    val data: Data
)

data class Data(
    val count: Int,
    val total: Int,
    val items: List<CartItem>,
    val products: List<CartProduct> // Ensure this is correctly defined in your Data class
)
data class CartProduct(
    val id: Int,
    val title: String,
    val slug: String,
    val description: String,
    val price: String,
    val quantity: Int, // This field might be redundant here if you're using cartItems to track quantity
    val status: String?,
    val created_at: String,
    val updated_at: String,
    val product_images: List<ProductImage>,
    val category_id: Int,
    val category: Category,
    val brand_id: Int,
    val brand: Brand
)

data class CartItem(
    val productId: Int,
    val quantity: Int
)

data class ProductImage(
    val id: Int,
    val product_id: Int,
    val image: String
)

data class Category(
    val id: Int,
    val name: String,
    val slug: String
)

data class Brand(
    val id: Int,
    val name: String,
    val slug: String
)
