package com.example.techmall.models

class AddProductModel {
    var p_brand:String? = null
    var p_model:String? = null
    var p_os:String? = null
    var p_processor:String? = null
    var p_memory:String? = null
    var p_display:String? = null
    var p_touch:String? = null
    var p_condition:String? = null
    var p_price:String? = null
    var p_stock:String? = null
    var p_uid: String? = null
    var p_imgurl:String? = null
    var p_category:String? = null

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
        c_stock: String?,
        c_uid: String?,
        c_imgurl: String?,
        c_category:String?
    ) {
        this.p_brand = c_brand
        this.p_model = c_model
        this.p_os = c_os
        this.p_processor = c_processor
        this.p_memory = c_memory
        this.p_display = c_display
        this.p_touch = c_touch
        this.p_condition = c_condition
        this.p_price = c_price
        this.p_stock = c_stock
        this.p_imgurl = c_imgurl
        this.p_uid = c_uid
        this.p_category = c_category
    }

    constructor()
}