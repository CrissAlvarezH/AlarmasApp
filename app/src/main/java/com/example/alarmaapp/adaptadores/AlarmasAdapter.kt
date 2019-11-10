package com.example.alarmaapp.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmaapp.R
import com.example.alarmaapp.modelos.Alarma

class AlarmasAdapter (alarmas: MutableList<Alarma>) : RecyclerView.Adapter<AlarmasAdapter.AlarmasViewHolder>() {

    var alarmas: MutableList<Alarma> = alarmas
        set(value) {
            field = value;
            notifyDataSetChanged()
        }

    interface OnHabilitarAlarmaListener {
        fun onHabilitarAlarma(alarma: Alarma, posicion: Int)
    }

    interface OnLongClickAlarmaListener {
        fun onLongClickAlarma(alarma: Alarma, posicion: Int)
    }

    var onHabilitarAlarmaListener: OnHabilitarAlarmaListener? = null
    var onLongClickAlarmaListener: OnLongClickAlarmaListener? = null

    inner class AlarmasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtHora: TextView = itemView.findViewById(R.id.item_txt_hora)
        val txtAmPm: TextView = itemView.findViewById(R.id.item_txt_am_pm)
        val switchOnOff: Switch = itemView.findViewById(R.id.item_switch)

        init {
            switchOnOff.setOnCheckedChangeListener {_, isChecked ->

                alarmas[adapterPosition].habilitada = isChecked

                onHabilitarAlarmaListener?.onHabilitarAlarma(alarmas[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {_ ->

                onLongClickAlarmaListener?.onLongClickAlarma(alarmas[adapterPosition], adapterPosition)

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmasViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarma, parent, false)

        return AlarmasViewHolder(item)
    }

    override fun onBindViewHolder(holder: AlarmasViewHolder, position: Int) {
        // Quitamos el listener para que no se active al setear el campo del swith aquí
        val listenerAux = onHabilitarAlarmaListener;
        onHabilitarAlarmaListener = null

        val alarma = alarmas[position];

        holder.txtHora.text = alarma.horaFormateada;
        holder.txtAmPm.text = alarma.amPm
        holder.switchOnOff.isChecked = alarma.habilitada

        onHabilitarAlarmaListener = listenerAux
    }

    override fun getItemCount(): Int {
        return alarmas.size
    }

    fun agregarAlarma(alarma: Alarma) {
        alarmas.add(alarma)
        notifyItemInserted( alarmas.size - 1 )
    }

    fun eliminarAlarma(alarma: Alarma) {
        val indice = alarmas.indexOf(alarma)

        alarmas.remove(alarma)

        notifyItemRemoved(indice)
    }
}