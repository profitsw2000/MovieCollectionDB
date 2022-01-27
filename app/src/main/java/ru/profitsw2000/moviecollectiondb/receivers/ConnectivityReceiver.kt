package ru.profitsw2000.moviecollectiondb.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.time.Duration

private const val tag = "MyReceiver"

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        if (extras != null){
            val networkInfo = "${extras.get("networkInfo")}"
            val connected = "state: CONNECTED/CONNECTED"
            if (connected in networkInfo) {
                Toast.makeText(context,"Internet connection is on.",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Your internet connection was lost. Please, switch internet on and reload page.",Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d(tag, "No extras")
        }
    }
}