package ru.profitsw2000.moviecollectiondb.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import java.time.Duration

private const val tag = "MyReceiver"

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        Log.d(tag, "NEXT MESSAGE:")
        intent.action?.let { Log.d(tag, it) }
        Log.d(tag, intent.component.toString())
        if (extras != null){
            val networkInfo = "${extras.get("networkInfo")}"
            val connected = "state: CONNECTED/CONNECTED"
            if (connected in networkInfo) {
                Toast.makeText(context,"Connected",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Not Connected",Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d(tag, "No extras")
        }
    }
}