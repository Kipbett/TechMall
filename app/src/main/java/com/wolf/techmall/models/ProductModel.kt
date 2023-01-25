package com.wolf.techmall.models

class ProductModel {
    var product_image:String? = null
    var product_name:String? = null
    var prod_category:String? = null
    var product_initial_price:String? = null
    var product_seller_id:String? = null

    constructor(product_image: String?, product_name: String?, product_initial_price: String?) {
        this.product_image = product_image
        this.product_name = product_name
        this.product_initial_price = product_initial_price
    }

    constructor(
        product_image: String?,
        product_name: String?,
        prod_category: String?,
        product_initial_price: String?
    ) {
        this.product_image = product_image
        this.product_name = product_name
        this.prod_category = prod_category
        this.product_initial_price = product_initial_price
    }


}