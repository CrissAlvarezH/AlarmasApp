package com.example.alarmaapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.alarmaapp.modelos.Alarma

@Dao
interface AlarmaDAO {

    @Insert
    fun insert(alarma: Alarma)

    @Query("SELECT * FROM alarmas")
    fun getTodas(): MutableList<Alarma>

    @Delete
    fun delete(alarma: Alarma)

}