package com.example.elivia.Services

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log

class ContactsService(val context: Context) {

    var crContacts: Cursor? = null
    val TAG = "Elivia"

    public fun readContacts(contactName: String) {
        var id: String
        var name: String
        var photoUri: String
        var order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        var cr = context.contentResolver
        crContacts = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?", arrayOf(contactName), order)
        if (crContacts != null) {
            println(crContacts!!.getString(crContacts!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
        }
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
        crContacts!!.close()
    }
}