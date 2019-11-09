package com.example.alarmaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val alarmas = mutableListOf(
            Alarma(
                hora = "30:09",
                habilitada = true
            ),
            Alarma(
                hora = "40:42",
                habilitada = true
            ),
            Alarma(
                hora = "01:72",
                habilitada = true
            ),
            Alarma(
                hora = "32:06",
                habilitada = true
            )
        )

        alarmasAdapter = AlarmasAdapter(alarmas)
        recyclerAlarmas?.adapter = alarmasAdapter

        toggleVisibilidadTxtNoHayAlarmas()
    }

    private fun toggleVisibilidadTxtNoHayAlarmas() {
        alarmasAdapter?.let {
            contNoHayAlarmas?.visibility = if ( it.itemCount > 0 ) View.GONE else View.VISIBLE
        }
    }
}
