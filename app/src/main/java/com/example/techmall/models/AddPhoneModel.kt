package com.example.techmall.models

class AddPhoneModel {

    var p_brand:String? = null
    var p_model:String? = null
    var p_os:String? = null
    var p_battery:String? = null
    var p_memory:String? = null
    var p_display:String? = null
    var p_condition:String? = null
    var p_price:String? = null
    var p_stock:String? = null
    var p_uid:String? = null
    var p_imgurl:String? = null
    var p_category:String? = null
    var p_color:String? = null
    var p_additional:String? = null


    constructor(
        p_brand: String?,
        p_model: String?,
        p_os: String?,
        p_battery: String?,
        p_memory: String?,
        p_display: String?,
        p_condition: String?,
        p_price: String?,
        p_stock: String?,
        p_uid: String?,
        p_imgurl:String?,
        p_category:String,
        p_color:String?,
        p_additional:String?
    ) {
        this.p_brand = p_brand
        this.p_model = p_model
        this.p_os = p_os
        this.p_battery = p_battery
        this.p_memory = p_memory
        this.p_display = p_display
        this.p_condition = p_condition
        this.p_price = p_price
        this.p_stock = p_stock
        this.p_uid = p_uid
        this.p_imgurl = p_imgurl
        this.p_category = p_category
        this.p_color = p_color
        this.p_additional = p_additional
    }
}