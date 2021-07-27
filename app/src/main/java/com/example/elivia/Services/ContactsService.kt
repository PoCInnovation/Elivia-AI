package com.example.elivia.Services

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import java.lang.Exception

class ContactsService(val context: Context) {

    var crContacts: Cursor? = null
    val TAG = "Elivia"

    public fun readContacts(contactName: String): String? {
        var number: String? = null;
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?",
            arrayOf(contactName),
            null
        )
            ?: return null
        if (cursor.moveToFirst()) {
            val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            val phones = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                null,
                null
            )
                ?: return null;
            if (phones.moveToFirst())
                number =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phones.close();
        }
        cursor.close();
        return number;
    }
}