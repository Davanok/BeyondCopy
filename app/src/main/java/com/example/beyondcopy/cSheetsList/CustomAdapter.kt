package com.example.beyondcopy.cSheetsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.CharactersDataBase

class CustomAdapter(private val dataSet: List<CharactersDataBase>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cName: TextView by lazy {view.findViewById(R.id.cName)}
        val cClass: TextView by lazy {view.findViewById(R.id.cClass)}
        val cRace: TextView by lazy {view.findViewById(R.id.cRace)}
        val level: TextView by lazy {view.findViewById(R.id.level)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_card, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cName.text = dataSet[position].cName
        holder.cClass.text = dataSet[position].cClass
        holder.cRace.text = dataSet[position].cRace
        holder.level.text = dataSet[position].lvl.toString()

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("id", dataSet[position].id)
            it.findNavController().navigate(R.id.characterSheet, bundle)
        }
    }
}