package com.example.techmall.models

class UserModel {
    var user_email:String? = null
    var user_address:String? = null
    var user_phone:String? = null

    constructor(user_email: String?, user_address: String?, user_phone:String?) {
        this.user_email = user_email
        this.user_address = user_address
        this.user_phone = user_phone
    }

    constructor()

}