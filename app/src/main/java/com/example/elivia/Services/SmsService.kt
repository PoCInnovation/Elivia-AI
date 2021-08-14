package com.example.elivia.Services

import android.content.Context
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Toast

class SmsService(val context: Context) {
    public fun sendMessage(number: String, message: String) {

        val number: String = "555"
        val msg: String = "test"
        if (TextUtils.isDigitsOnly(number)) {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number, null, msg, null, null)
            Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Le numéro est invalide", Toast.LENGTH_SHORT).show()
        }
    }
}