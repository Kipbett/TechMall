package com.wolf.techmall.models

class PhoneDetails {

    var product_name:String? = null
    var product_image:String? = null
    var product_stock:String? = null
    var product_price:String? = null
    var product_category:String? = null

    constructor(
        product_name: String?,
        product_image: String?,
        product_stock: String?,
        product_price: String?
    ) {
        this.product_name = product_name
        this.product_image = product_image
        this.product_stock = product_stock
        this.product_price = product_price
    }

    constructor(
        product_name: String?,
        product_image: String?,
        product_stock: String?,
        product_price: String?,
        product_category: String?
    ) {
        this.product_name = product_name
        this.product_image = product_image
        this.product_stock = product_stock
        this.product_price = product_price
        this.product_category = product_category
    }


}