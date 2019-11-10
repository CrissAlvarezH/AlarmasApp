package com.example.alarmaapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

const val COD_CREAR_ALARMA = 354

class MainActivity : AppCompatActivity() {

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

                    val hora = data?.getIntExtra( CrearAlarmaActivity.Args.HORA, 0 ) ?: 0
                    val minutos = data?.getIntExtra( CrearAlarmaActivity.Args.MINUTOS, 0 ) ?: 0

                    Log.v("PRUEBA", "Hora: ${ hora }, minutos: ${ minutos }")

                    val alarma = Alarma(hora, minutos, habilitada = true)
                    alarmasAdapter?.agregarAlarma(alarma)

                    toggleVisibilidadTxtNoHayAlarmas()
                }
            }

        }
    }
}
