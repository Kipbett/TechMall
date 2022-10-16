package com.example.techmall.models

class ProductModel {
    var product_image:Int? = null
    var product_name:String? = null
    var prod_category:String? = null
    var product_initial_price:String? = null
    var product_discounted_price:String? = null
    var product_discount_percentage:String? = null



    constructor(product_image:Int, product_name:String, product_initial_price:String,
                product_discounted_price:String, product_discount_percentage:String, prod_category:String){
        this.product_image = product_image
        this.product_name = product_name
        this.product_initial_price = product_initial_price
        this.product_discounted_price = product_discounted_price
        this.product_discount_percentage = product_discount_percentage
        this.prod_category = prod_category
    }
}