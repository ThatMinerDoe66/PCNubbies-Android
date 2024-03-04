data class ProductResponse(
    val data: List<Product>
)

data class Product(
    val id: Int,
    val title: String,
    val slug: String,
    val description: String,
    val price: String,
    val quantity: Int,
    val status: String?,
    val created_at: String,
    val updated_at: String,
    val product_images: List<ProductImage>,
    val category_id: Int,
    val category: Category,
    val brand_id: Int,
    val brand: Brand
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