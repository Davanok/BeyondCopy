package com.example.beyondcopy.csheet.sheets.skills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R

class SkillsListAdapter(
    private val skills: IntArray,
    private val skillModifiers: BooleanArray,
    private val skillNames: Array<String>,
    private val modifierNames: Array<String>
): RecyclerView.Adapter<SkillsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val skillWithBonus: RadioButton by lazy{ view.findViewById(R.id.skillWithBonus) }
        val skillModifier: TextView by lazy { view.findViewById(R.id.skillModifier) }
        val skillName: TextView by lazy { view.findViewById(R.id.skillName) }
        val skillBonus: TextView by lazy{ view.findViewById(R.id.skillBonus) }
    }
    private fun numToSkill(num: Int):Int{
        return when(num){
            0->1
            1->4
            2->3
            3->0
            4->5
            5->3
            6->4
            7->5
            8->3
            9->4
            10->3
            11->4
            12->5
            13->5
            14->3
            15->1
            16->1
            17->4
            else -> -1
        }
    }
    private fun numToModifier(num: Int):String{
        return modifierNames[numToSkill(num)]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.skills, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = skills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.skillWithBonus.isChecked = skillModifiers[position]
        holder.skillModifier.text = numToModifier(position)
        holder.skillName.text = skillNames[position]
        holder.skillBonus.text = skills[position].toString()
    }
}