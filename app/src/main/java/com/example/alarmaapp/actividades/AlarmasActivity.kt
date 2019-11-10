package com.example.alarmaapp.actividades

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.alarmaapp.R
import com.example.alarmaapp.adaptadores.AlarmasAdapter
import com.example.alarmaapp.basedatos.AppDataBase
import com.example.alarmaapp.modelos.Alarma
import com.example.alarmaapp.utils.AlarmasUtils
import java.util.*

const val COD_CREAR_ALARMA = 354

class MainActivity : AppCompatActivity(), AlarmasAdapter.OnHabilitarAlarmaListener,
    AlarmasAdapter.OnLongClickAlarmaListener {

    var contNoHayAlarmas: LinearLayout? = null
    var recyclerAlarmas: RecyclerView? = null

    var alarmasAdapter: AlarmasAdapter? = null

    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarmas)

        enlazarXML();

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            getString(R.string.db_name)
        ).build()

        Thread { // El acceso a la base de datos se debe hacer en otro hilo
            val alarmas = db?.alarmaDAO()?.getTodas() ?: mutableListOf()

            runOnUiThread {
                alarmasAdapter?.alarmas = alarmas
                toggleVisibilidadTxtNoHayAlarmas()
            }

        }.start()
    }

    private fun enlazarXML() {
        contNoHayAlarmas = findViewById(R.id.cont_no_hay_alarmas)
        recyclerAlarmas = findViewById(R.id.recycler_alarmas)

        val lmAlarmas = LinearLayoutManager(this)
        recyclerAlarmas?.layoutManager = lmAlarmas

        alarmasAdapter = AlarmasAdapter(mutableListOf())
        alarmasAdapter?.onHabilitarAlarmaListener = this
        alarmasAdapter?.onLongClickAlarmaListener = this
        recyclerAlarmas?.adapter = alarmasAdapter

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

                    Thread { // El acceso a la base de datos se debe hacer en otro hilo

                        val id = db?.alarmaDAO()?.insert(alarma) ?: 0
                        Log.v("PROBANDO", "Id insertado: &${ id }")

                        alarma.id = id

                        runOnUiThread {

                            alarmasAdapter?.agregarAlarma(alarma)

                            toggleVisibilidadTxtNoHayAlarmas()
                        }

                    }.start()
                }
            }

        }
    }

    override fun onHabilitarAlarma(alarma: Alarma, posicion: Int) {

        Thread {

            db?.alarmaDAO()?.actualizarHabilitada(alarma.id, alarma.habilitada)

            runOnUiThread {

                if (alarma.habilitada) {

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, alarma.hora)
                    calendar.set(Calendar.MINUTE, alarma.minutos)
                    calendar.set(Calendar.SECOND, 0)

                    AlarmasUtils.habilitarAlarma(this, calendar, alarma.horaFormateada, alarma.id.toInt())

                } else {
                    AlarmasUtils.deshabilidatAlarma(this, alarma.id.toInt())
                }
            }
        }.start()
    }

    override fun onLongClickAlarma(alarma: Alarma, posicion: Int) {

        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Seguro que quieres eliminar la alarma de ${ alarma.horaFormateada }${ alarma.amPm }?")
            .setPositiveButton("SI") {_, _ ->

                Thread {

                    db?.alarmaDAO()?.delete(alarma)

                    runOnUiThread {

                        alarmasAdapter?.eliminarAlarma(alarma)

                        toggleVisibilidadTxtNoHayAlarmas()

                    }
                }.start()

            }
            .setNegativeButton("NO", null)
            .create()

        if ( !isFinishing )
            dialog.show()
    }

}
