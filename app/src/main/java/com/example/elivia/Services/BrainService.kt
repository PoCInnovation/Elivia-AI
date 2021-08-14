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
import androidx.core.content.ContextCompat.startActivity

class BrainService(val context: Context) {
    private val messages: ArrayList<Messages> = ArrayList()
    private val alarmService: AlarmService;
    private val contactService: ContactsService;
    private val applicationService: ApplicationService;
    private val smsService: SmsService;
    private val searchService: SearchService;

    init {
        alarmService = AlarmService(context);
        contactService = ContactsService(context);
        applicationService = ApplicationService(context);
        smsService = SmsService(context);
        searchService = SearchService(context);
    }

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
            "hello" -> return Messages("other", "hello", Calendar.getInstance().timeInMillis, "000000");
            "open" -> return openApp(response.body()!!);
            "search" -> return makeASearch(response.body()!!)
            "music" -> return startMusic(response.body()!!);
            "call" -> return makeACall(response.body()!!);
            "message" -> return sendMessage(response.body()!!);
            "alarm" -> return setAnAlarme(response.body()!!);
            "reminder" -> return setAReminder(response.body()!!);
        }
        return Messages("other", "Je n 'ai pas compris", Calendar.getInstance().timeInMillis, "000000");
    }


    private fun openApp(responce: RasaResponse) : Messages {
        val appName = "messaging";
        val packageName = applicationService.getAppId(appName)
                ?: return (Messages("other", "L'application $appName n'a pas été trouvé sur votre téléphone", Calendar.getInstance().timeInMillis, ""));
        if (applicationService.openAppById(packageName))
            return (Messages("other", "L'application est ouvert", Calendar.getInstance().timeInMillis, ""))
        else
            return (Messages("other", "L'application $appName n'a pas pu démarrer", Calendar.getInstance().timeInMillis, ""))
    }

    private fun sendMessage(responce: RasaResponse) : Messages {
        val number: String = when (responce.entities.firstOrNull()?.entity) {
            "number" -> {
                (responce.entities.first())?.value ?: "sfg";
            }
            "person" -> {
                val person = (responce.entities.first())?.value ?: "sfg";
                contactService.readContacts(person) ?: return Messages("other", "$person n'existe pas dans vos contacts", Calendar.getInstance().timeInMillis, "");
            }
            else -> {
                return (Messages("other", "Je n'ai pas bien compris", Calendar.getInstance().timeInMillis, ""))
            }
        }
        // TODO: value hardcoder dans le service
        smsService.sendMessage(number, "");
        return (Messages("other", "Le message est envoyé", Calendar.getInstance().timeInMillis, ""))
    }

    private fun makeACall(responce: RasaResponse) : Messages {

        val number: String = contactService.readContacts("Sacha") ?: return Messages("other", "La personne n'existe pas", Calendar.getInstance().timeInMillis, "")
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

    private fun setAnAlarme(responce: RasaResponse) : Messages {
        // TODO: en fonction des entities
        val hour = 10;
        val min = 30;
        alarmService.createAlarm("", hour, min);

        return (Messages("other", "Une alarme sonnera à $hour h $min", Calendar.getInstance().timeInMillis, ""))
    }

    private fun setAReminder(responce: RasaResponse) : Messages {
        // TODO: en fonction des entities
        val time = 60;
        //alarmService.startTimer("", time);
        val cal = Calendar.getInstance()
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("beginTime", cal.timeInMillis)
        intent.putExtra("allDay", true)
        intent.putExtra("rrule", "FREQ=YEARLY")
        intent.putExtra("endTime", cal.timeInMillis + 60 * 60 * 1000)
        intent.putExtra("title", "A Test Event from android app")
        context.startActivity(intent)

        return (Messages("other", "Le timer sonnera dans $time seconde", Calendar.getInstance().timeInMillis, ""))
    }

    private fun startMusic(responce: RasaResponse) : Messages {
        return (Messages("other", "Je n'ai pas trouvé ce morceau de musique", Calendar.getInstance().timeInMillis, ""))
    }

    private fun makeASearch(responce: RasaResponse) : Messages {
        searchService.searchWeb(responce.text)

        return (Messages("other", "your result is: ", Calendar.getInstance().timeInMillis, ""))
    }
}