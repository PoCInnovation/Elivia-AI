package com.example.elivia.Services

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.elivia.Components.Adapter.MessageAdapter
import com.example.elivia.Models.Messages
import com.example.elivia.Models.RasaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class BrainService(val context: Context) {
    private val messages: ArrayList<Messages> = ArrayList()
    fun takeAction(message: Messages, adapter: MessageAdapter){
        messages.add(message);
        val call = ChatService.create().postMessage(message)

        call.enqueue(object : Callback<RasaResponse> {
            override fun onResponse(call: Call<RasaResponse>, response: Response<RasaResponse>) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Response was not successful", Toast.LENGTH_SHORT).show()
                }
                adapter.addMessage(intentToAction(response));
            }

            override fun onFailure(call: Call<RasaResponse>, t: Throwable) {
                println("error : " + t)
                Toast.makeText(context, "Error when calling the service", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun intentToAction(response: Response<RasaResponse>): Messages {
        println(response);
        println(response.body());
        when (response.body()?.intent?.name) {
            "bonjour" -> return Messages("other", "hello", Calendar.getInstance().timeInMillis, "000000");
        }
        return Messages("other", "Pas de souci !", Calendar.getInstance().timeInMillis, "000000");
        /*return if (message.text == "ouvre le calendrier") {
            openCalendar()
        } else if (message.text.contains("appel")) {
            makeACall(message.text);
        } else {
            Messages("other", "Pas de souci !", Calendar.getInstance().timeInMillis, "000000");
        }*/
    }

    private fun openCalendar() : Messages{
        val pm: PackageManager = context.getPackageManager()

        try {
            val it: Intent? = pm.getLaunchIntentForPackage("com.simplemobiletools.calendar")
            if (null != it) context.startActivity(it)
        } catch (e: ActivityNotFoundException) {
            println(e);
        }
        return (Messages("other", "Le calendrier est ouvert", Calendar.getInstance().timeInMillis, ""))
    }

    private fun makeACall(str: String) : Messages {

        val number: String = "0670068253";
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            } else {
                val dial = "tel:$number"
                context.startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        } else {
            Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        }
        return (Messages("other", "Appel en cours", Calendar.getInstance().timeInMillis, ""))
    }

    private fun makeASearch(str: String) : Messages {
        return (Messages("other", "Appel en cours", Calendar.getInstance().timeInMillis, ""))
    }
}