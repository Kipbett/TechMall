package com.example.techmall.models

class SellersModel {

    var prod_image:Int? = null
    var prod_name:String? = null
    var price:String? = null
    var stock:String? = null

    constructor(prod_image: Int?, prod_name:String?, price: String?, stock: String?) {
        this.prod_image = prod_image
        this.price = price
        this.stock = stock
        this.prod_name = prod_name
    }
}