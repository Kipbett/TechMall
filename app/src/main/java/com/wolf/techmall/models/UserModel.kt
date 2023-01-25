package com.wolf.techmall.models

class UserModel {
    var user_email:String? = null
    var user_address:String? = null
    var user_phone:String? = null
    var user_name:String? = null
    var user_image:String? = null

    constructor(user_email: String?, user_address: String?, user_phone:String?, user_name:String?, user_image:String?) {
        this.user_email = user_email
        this.user_address = user_address
        this.user_phone = user_phone
        this.user_name = user_name
        this.user_image = user_image
    }

    constructor()

}