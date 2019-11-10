package com.example.alarmaapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmaapp.utils.AlarmasUtils
import com.example.alarmaapp.utils.NotificacionesUtils

class AlarmaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("PROBANDO", "!!!!!!!!!!!!!!!!!!! ALARMA !!!!!!!!!!!!!!!!!!!!!")

        NotificacionesUtils.lanzarNotificacion(
            context,
            titulo = "¡¡¡Alarma de ${ intent.extras?.getString(AlarmasUtils.Extras.HORA) } !!!"
        )

    }
}
