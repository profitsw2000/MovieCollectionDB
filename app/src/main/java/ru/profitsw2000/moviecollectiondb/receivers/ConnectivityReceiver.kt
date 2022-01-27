package ru.profitsw2000.moviecollectiondb.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val tag = "DebugReceiver"

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        intent.action?.let { Log.d(tag, it) }
        Log.d(tag, intent.component.toString())
        if (extras != null){
            for (str: String in extras.keySet()){
                Log.d(tag, "key [$str]: ${extras.get(str)}")
            }
        } else {
            Log.d(tag, "No extras")
        }
    }
}