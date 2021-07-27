package com.example.elivia.Services

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import java.lang.Exception

class ContactsService(val context: Context) {

    var crContacts: Cursor? = null
    val TAG = "Elivia"

    public fun readContacts(contactName: String) : String? {
        var id: String
        var name: String
        var photoUri: String
        var order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        var cr = context.contentResolver
        var tmp = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?", arrayOf(contactName), null)
        if (tmp == null)
            return null;
        tmp.moveToFirst();
        return tmp.getString(tmp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        while (tmp.moveToNext()) {
            tmp.columnNames.iterator().forEach {
                println(it + " " + tmp.getColumnIndex(it));
            }
            println(tmp.getString(tmp.getColumnIndex("display_name")));
        }
        /*try {
            crContacts = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?", arrayOf(contactName), order)
        } catch (e: RuntimeException) {
            println("error on query $e");
        }*/
        /*println(crContacts);
        if (crContacts != null) {
            println(crContacts!!.getString(crContacts!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
        }*/
// dans config name spacy LOCATION => LOC
        /*if (crContacts == null)
            return
        while (crContacts!!.moveToNext()) {
            id = crContacts!!.getString(crContacts!!.getColumnIndex(ContactsContract.Contacts._ID))
            name = crContacts!!.getString(crContacts!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

            if (Integer.parseInt(crContacts!!.getString(crContacts!!.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                //Cursor crPhones = cr.query (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                var crPhones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)

                Log.d(TAG, "NAME: " + name)

                while (crPhones!!.moveToNext()) {
                    var phone = crPhones!!.getString(crPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    Log.d(TAG, "\tPHONE: " + phone)
                }
                crPhones!!.close()
            }
        }*/
        //crContacts!!.close()
    }
}