package com.example.elivia.Services

import android.app.SearchManager
import android.content.Context
import android.content.Intent

class SearchService(val context: Context) {

    fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

}