package com.example.alarmaapp.actividades

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import com.example.alarmaapp.R

class CrearAlarmaActivity : AppCompatActivity() {

    class Args {
        companion object {
            const val HORA = "hora"
            const val MINUTOS = "minutos"
        }
    }

    var timePicker: TimePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_alarma)

        timePicker = findViewById(R.id.timepicker)
    }

    fun clickGuardar(btn: View) {
        var hora: Int?
        var minutos: Int?

        if ( android.os.Build.VERSION.SDK_INT >= 23 ) {

            hora = timePicker?.hour
            minutos = timePicker?.minute

        } else {

            hora = timePicker?.currentHour
            minutos = timePicker?.currentMinute
        }

        val intent = Intent().apply {
            putExtra(Args.HORA, hora)
            putExtra(Args.MINUTOS, minutos)
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun clickCancelar(btn: View) {
        finish()
    }
}
