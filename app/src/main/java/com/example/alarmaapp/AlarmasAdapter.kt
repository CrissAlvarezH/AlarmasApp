package com.example.alarmaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmasAdapter (alarmas: MutableList<Alarma>) : RecyclerView.Adapter<AlarmasAdapter.AlarmasViewHolder>() {

    var alarmas: MutableList<Alarma> = alarmas
        set(value) {
            field = value;
            notifyDataSetChanged()
        }

    inner class AlarmasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtHora: TextView = itemView.findViewById(R.id.item_txt_hora)
        val txtAmPm: TextView = itemView.findViewById(R.id.item_txt_am_pm)
        val switchOnOff: Switch = itemView.findViewById(R.id.item_switch)

        init {
            switchOnOff.setOnCheckedChangeListener {_, isChecked ->

                alarmas[adapterPosition].habilitada = isChecked

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmasViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarma, parent, false)

        return AlarmasViewHolder(item)
    }

    override fun onBindViewHolder(holder: AlarmasViewHolder, position: Int) {
        val alarma = alarmas[position];

        holder.txtHora.text = alarma.hora;
        holder.txtAmPm.text = "AM"
        holder.switchOnOff.isSelected = alarma.habilitada
    }

    override fun getItemCount(): Int {
        return alarmas.size
    }

}