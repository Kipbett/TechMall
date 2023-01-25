package com.wolf.techmall.models

class ShoppingCart {

    var prod_name:String? = null
    var count:Int? = null
    var amount:Int? = null
    var total_amount:Int? = null

    constructor(prod_name: String?, count: Int?, amount: Int?, total_amount: Int?) {
        this.prod_name = prod_name
        this.count = count
        this.amount = amount
        this.total_amount = total_amount
    }

    fun getTotalAmount(count:Int, amount:Int):Int{
        this.count = count
        this.total_amount = total_amount
        total_amount = count * amount
        return total_amount as Int
    }

    fun deleteItem(){}

    fun addItem(){}

    fun sumTotal(){}
}