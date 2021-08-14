package com.example.elivia.Services

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import java.util.*

class ApplicationService(val context: Context) {
    private val packageManager : PackageManager = context.packageManager;
    public fun getAppId(appName: String): String? {
        val appsData = packageManager.getInstalledPackages(0)
                .filter { it.packageName.contains(appName.toLowerCase(Locale.ROOT)) } // any filtering you want before loading
                .firstOrNull() ?: return null
        return appsData.packageName;
    }

    public fun openAppById(packageName: String): Boolean {

        try {
            val it: Intent? = packageManager.getLaunchIntentForPackage(packageName);
            if (null != it) context.startActivity(it)
            else return false
        } catch (e: ActivityNotFoundException) {
            println(e);
            return false;
        }
        return true;
    }
}