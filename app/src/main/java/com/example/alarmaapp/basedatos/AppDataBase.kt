package com.example.alarmaapp.basedatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmaapp.dao.AlarmaDAO
import com.example.alarmaapp.modelos.Alarma

@Database(entities = arrayOf(Alarma::class), version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun alarmaDAO(): AlarmaDAO

}