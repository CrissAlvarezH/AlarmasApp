package com.example.alarmaapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import com.example.alarmaapp.actividades.CrearAlarmaActivity
import com.example.alarmaapp.receivers.AlarmaReceiver
import java.util.*

class AlarmasUtils {

    class Extras {
        companion object {
            const val HORA = "hora"
        }
    }

    companion object {

        fun habilitarAlarma(contexto: Context, calendar: Calendar, hora: String, codAlarma: Int) {
            val alarmManager: AlarmManager = contexto.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(contexto, AlarmaReceiver::class.java).apply {
                putExtra(CrearAlarmaActivity.Args.HORA, hora)
            }

            val pendingIntent = PendingIntent.getBroadcast(contexto, codAlarma, intent, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }

        fun deshabilidatAlarma(contexto: Context, codAlarma: Int) {
            val alarmManager = contexto.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(contexto, AlarmaReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(contexto, codAlarma, intent, 0)

            alarmManager.cancel(pendingIntent)
        }
    }

}