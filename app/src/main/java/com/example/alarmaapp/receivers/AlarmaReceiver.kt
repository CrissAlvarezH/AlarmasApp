package com.example.alarmaapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("PROBANDO", "!!!!!!!!!!!!!!!!!!! ALARMA !!!!!!!!!!!!!!!!!!!!!")

    }
}
