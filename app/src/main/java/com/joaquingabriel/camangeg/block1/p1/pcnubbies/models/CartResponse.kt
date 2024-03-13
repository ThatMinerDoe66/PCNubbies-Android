package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

data class CartResponse(
    val data: Data
)

data class Data(
    val count: Int,
    val total: Double,
    val items: List<CartItem>,
    val products: List<CartProduct> // Ensure this is correctly defined in your Data class
)
data class CartProduct(
    val id: Int,
    val title: String,
    val slug: String,
    val description: String,
    val price: String,
    val stockQuantity: Int, // This represents the total quantity of the product available in stock
    var cartQuantity: Int, // This represents the quantity of the product in the cart
    val status: String?,
    val created_at: String,
    val updated_at: String,
    val category_id: Int,
    val brand_id: Int,
    val product_images: List<ProductImage>? // Assuming this is a list of objects with an 'image' property
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
