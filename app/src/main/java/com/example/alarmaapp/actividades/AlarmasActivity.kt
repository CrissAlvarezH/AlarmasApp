package com.example.alarmaapp.actividades

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmaapp.R
import com.example.alarmaapp.adaptadores.AlarmasAdapter
import com.example.alarmaapp.modelos.Alarma
import com.example.alarmaapp.receivers.AlarmaReceiver
import com.example.alarmaapp.utils.AlarmasUtils
import java.util.*

const val COD_CREAR_ALARMA = 354

class MainActivity : AppCompatActivity(), AlarmasAdapter.OnHabilitarAlarmaListener {

    var contNoHayAlarmas: LinearLayout? = null
    var recyclerAlarmas: RecyclerView? = null

    var alarmasAdapter: AlarmasAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enlazarXML();

    }

    private fun enlazarXML() {
        contNoHayAlarmas = findViewById(R.id.cont_no_hay_alarmas)
        recyclerAlarmas = findViewById(R.id.recycler_alarmas)

        val lmAlarmas = LinearLayoutManager(this)
        recyclerAlarmas?.layoutManager = lmAlarmas

        alarmasAdapter = AlarmasAdapter(mutableListOf())
        alarmasAdapter?.onHabilitarAlarmaListener = this
        recyclerAlarmas?.adapter = alarmasAdapter

        toggleVisibilidadTxtNoHayAlarmas()
    }

    private fun toggleVisibilidadTxtNoHayAlarmas() {
        alarmasAdapter?.let {
            contNoHayAlarmas?.visibility = if ( it.itemCount > 0 ) View.GONE else View.VISIBLE
        }
    }

    fun clickAddAlarma(btn: View) {

        startActivityForResult(
            Intent(this, CrearAlarmaActivity::class.java),
            COD_CREAR_ALARMA
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                COD_CREAR_ALARMA ->{

                    val hora = data?.getIntExtra(CrearAlarmaActivity.Args.HORA, 0 ) ?: 0
                    val minutos = data?.getIntExtra(CrearAlarmaActivity.Args.MINUTOS, 0 ) ?: 0

                    val alarma = Alarma(hora, minutos, habilitada = false)

                    alarmasAdapter?.agregarAlarma(alarma)

                    toggleVisibilidadTxtNoHayAlarmas()
                }
            }

        }
    }

    override fun onHablitiarAlarma(alarma: Alarma, posicion: Int) {

        if (alarma.habilitada) {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarma.hora)
            calendar.set(Calendar.MINUTE, alarma.minutos)
            calendar.set(Calendar.SECOND, 0)

            AlarmasUtils.habilitarAlarma(this, calendar, alarma.horaFormateada, posicion)

        } else {
            AlarmasUtils.deshabilidatAlarma(this, posicion)
        }

    }

}
