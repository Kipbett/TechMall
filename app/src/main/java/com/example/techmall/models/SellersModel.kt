package com.example.techmall.models

class SellersModel {

    var p_image:Int? = null
    var p_brand:String? = null
    var p_price:String? = null
    var p_stock:String? = null
    var p_categoty:String? = null



    constructor(p_image: Int?, p_brand:String?, p_price: String?, p_stock: String?, p_categoty:String?) {
        this.p_image = p_image
        this.p_price = p_price
        this.p_stock = p_stock
        this.p_brand = p_brand
        this.p_categoty = p_categoty
    }

    constructor()


}