package com.example.elivia.Services

import android.provider.ContactsContract

// class PhoneService {
// private val PROJECTION: Array<out String> = arrayOf(
// ContactsContract.Data._ID,
// ContactsContract.Data.MIMETYPE,
// ContactsContract.Data.DATA1,
// ContactsContract.Data.DATA2,
// ContactsContract.Data.DATA3,
// ContactsContract.Data.DATA4,
// ContactsContract.Data.DATA5,
// ContactsContract.Data.DATA6,
// ContactsContract.Data.DATA7,
// ContactsContract.Data.DATA8,
// ContactsContract.Data.DATA9,
// ContactsContract.Data.DATA10,
// ContactsContract.Data.DATA11,
// ContactsContract.Data.DATA12,
// ContactsContract.Data.DATA13,
// ContactsContract.Data.DATA14,
// ContactsContract.Data.DATA15
// )
//
// // Defines the selection clause
// private val SELECTION: String = "${ContactsContract.Data.LOOKUP_KEY} = ?"
// // Defines the array to hold the search criteria
// private val selectionArgs: Array<String> = arrayOf("")
// /*
// * Defines a variable to contain the selection value. Once you
// * have the Cursor from the Contacts table, and you've selected
// * the desired row, move the row's LOOKUP_KEY value into this
// * variable.
// */
// private var lookupKey: String? = null
//
// private val SORT_ORDER = ContactsContract.Data.MIMETYPE
//
// private const val DETAILS_QUERY_ID: Int = 0
//
// class DetailsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
// ...dd
// override fun onCreate(savedInstanceState: Bundle?) {
// ...
// // Initializes the loader framework
// loaderManager.initLoader(DETAILS_QUERY_ID, null, this)
//
//
// }