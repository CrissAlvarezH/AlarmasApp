package com.example.alarmaapp.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.math.min

@Entity (tableName = "alarmas")
class Alarma(
    var hora: Int,
    var minutos: Int,
    var habilitada: Boolean
) {

    @PrimaryKey (autoGenerate = true)
    var id: Long = 0
    var amPm: String = "AM"
    var horaFormateada: String = ""

    init {
        reformatearHora(hora, minutos)
    }

    /**
     * Convierte el formato de 24 a 12 horas, ademas coloca un cero delante de cada numero
     * en caso que sean de un solo digito
     */
    private fun reformatearHora(horaInt: Int, minutosInt: Int) {

        var nuevoMinutos: String = minutosInt.toString()

        if ( minutosInt < 10 ) nuevoMinutos = "0" + minutosInt

        if ( horaInt > 12) {
            val hora12 = horaInt - 12

            var nuevaHora: String = hora12.toString()

            if ( hora12 < 10 )
                nuevaHora = "0" + hora12

            horaFormateada = "${ nuevaHora }:${ nuevoMinutos }"
            amPm = "PM"

        } else {
            var nuevaHora: String = horaInt.toString()

            if ( horaInt < 10 )
                nuevaHora = "0" + horaInt

            horaFormateada = "${ nuevaHora }:${ nuevoMinutos }"
            amPm = "AM"
        }
    }

}