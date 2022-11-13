package com.example.techmall.models

class AddProductModel {
    var c_brand:String? = null
    var c_model:String? = null
    var c_os:String? = null
    var c_processor:String? = null
    var c_memory:String? = null
    var c_display:String? = null
    var c_touch:String? = null
    var c_condition:String? = null
    var c_price:String? = null
    var c_stock:String? = null

    constructor(
        c_brand: String?,
        c_model: String?,
        c_os: String?,
        c_processor: String?,
        c_memory: String?,
        c_display: String?,
        c_touch: String?,
        c_condition: String?,
        c_price: String?,
        c_stock: String?
    ) {
        this.c_brand = c_brand
        this.c_model = c_model
        this.c_os = c_os
        this.c_processor = c_processor
        this.c_memory = c_memory
        this.c_display = c_display
        this.c_touch = c_touch
        this.c_condition = c_condition
        this.c_price = c_price
        this.c_stock = c_stock
    }

    constructor()
}