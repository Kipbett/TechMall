package com.wolf.techmall.models

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import com.wolf.techmall.activities.MainActivity

class SendCall (context: Context){

    var context:Context? = null

    fun callUser(p_number:String) {
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:+$p_number")
        context?.startActivity(intent)
    }

    fun messageUser(p_number:String) {
        var intent = Intent(context, MainActivity::class.java)
        var pi = PendingIntent.getActivity(context, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(p_number, null, "Hell, I Want to Buy Your Product", pi, null)
    }
}