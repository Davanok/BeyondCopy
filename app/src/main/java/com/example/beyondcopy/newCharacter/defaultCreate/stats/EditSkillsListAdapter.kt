package com.example.beyondcopy.newCharacter.defaultCreate.stats

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.toInt

class EditSkillsListAdapter(
    private val viewModel: DefaultCreateStatsViewModel,
    private val profBonus: Int,
    private val nextBtn: Button? = null,
): RecyclerView.Adapter<EditSkillsListAdapter.ViewHolder>() {
    private var compare: Boolean = false
    private var compareStatsList: List<Int>? = null
    private var compareSavesList: List<Boolean>? = null
    private var compareSkillsList: List<Boolean>? = null
    constructor(
        viewModel: DefaultCreateStatsViewModel,
        profBonus: Int,
        nextBtn: Button,
        _compareStatsList: List<Int>,
        _compareSavesList: List<Boolean>,
        _compareSkillsList: List<Boolean>,
    ) : this(viewModel, profBonus, nextBtn) {
        compare = true
        compareStatsList = _compareStatsList
        compareSavesList = _compareSavesList
        compareSkillsList = _compareSkillsList
    }

    fun updateCompareLists(stats: List<Int>, saves: List<Boolean>, skills: List<Boolean>){
        compareStatsList = stats
        compareSavesList = saves
        compareSkillsList = skills
    }

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statsStrength: EditText by lazy { view.findViewById(R.id.statsStrength) }
        val statsDexterity: EditText by lazy { view.findViewById(R.id.statsDexterity) }
        val statsConstitution: EditText by lazy { view.findViewById(R.id.statsConstitution) }
        val statsIntelligence: EditText by lazy { view.findViewById(R.id.statsIntelligence) }
        val statsWisdom: EditText by lazy { view.findViewById(R.id.statsWisdom) }
        val statsCharisma: EditText by lazy { view.findViewById(R.id.statsCharisma) }

        val statsStrengthMod: TextView by lazy { view.findViewById(R.id.statsStrengthMod) }
        val statsDexterityMod: TextView by lazy { view.findViewById(R.id.statsDexterityMod) }
        val statsConstitutionMod: TextView by lazy { view.findViewById(R.id.statsConstitutionMod) }
        val statsIntelligenceMod: TextView by lazy { view.findViewById(R.id.statsIntelligenceMod) }
        val statsWisdomMod: TextView by lazy { view.findViewById(R.id.statsWisdomMod) }
        val statsCharismaMod: TextView by lazy { view.findViewById(R.id.statsCharismaMod) }

        val savesStrength: TextView by lazy { view.findViewById(R.id.savesStrength) }
        val savesDexterity: TextView by lazy { view.findViewById(R.id.savesDexterity) }
        val savesConstitution: TextView by lazy { view.findViewById(R.id.savesConstitution) }
        val savesIntelligence: TextView by lazy { view.findViewById(R.id.savesIntelligence) }
        val savesWisdom: TextView by lazy { view.findViewById(R.id.savesWisdom) }
        val savesCharisma: TextView by lazy { view.findViewById(R.id.savesCharisma) }

        val savesStrengthBtn: CheckBox by lazy { view.findViewById(R.id.savesStrengthBtn) }
        val savesDexterityBtn: CheckBox by lazy { view.findViewById(R.id.savesDexterityBtn) }
        val savesConstitutionBtn: CheckBox by lazy { view.findViewById(R.id.savesConstitutionBtn) }
        val savesIntelligenceBtn: CheckBox by lazy { view.findViewById(R.id.savesIntelligenceBtn) }
        val savesWisdomBtn: CheckBox by lazy { view.findViewById(R.id.savesWisdomBtn) }
        val savesCharismaBtn: CheckBox by lazy { view.findViewById(R.id.savesCharismaBtn) }

        val skillWithBonus: CheckBox by lazy { view.findViewById(R.id.editSkillWithBonus) }
        val skillModifier: TextView by lazy { view.findViewById(R.id.editSkillModifier) }
        val skillName: TextView by lazy { view.findViewById(R.id.editSkillName) }
        val skillBonus: TextView by lazy { view.findViewById(R.id.editSkillBonus) }
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
        return when(numToSkill(num)){
            0 -> context.getString(R.string.strengthShort)
            1 -> context.getString(R.string.dexterityShort)
            2 -> context.getString(R.string.constitutionShort)
            3 -> context.getString(R.string.intelligenceShort)
            4 -> context.getString(R.string.wisdomShort)
            5 -> context.getString(R.string.charismaShort)
            else -> context.getString(R.string.defaultTextShort)
        }
    }
    private fun getSkillName(num: Int): String{
        return when(num){
            0->context.getString(R.string.acrobatics)
            1->context.getString(R.string.animalHandling)
            2->context.getString(R.string.arcana)
            3->context.getString(R.string.athletics)
            4->context.getString(R.string.deception)
            5->context.getString(R.string.history)
            6->context.getString(R.string.insight)
            7->context.getString(R.string.intimidation)
            8->context.getString(R.string.investigation)
            9->context.getString(R.string.medicine)
            10->context.getString(R.string.nature)
            11->context.getString(R.string.perception)
            12->context.getString(R.string.performance)
            13->context.getString(R.string.persuasion)
            14->context.getString(R.string.religion)
            15->context.getString(R.string.sleightOfHand)
            16->context.getString(R.string.stealth)
            17->context.getString(R.string.survival)
            else -> context.getString(R.string.defaultCardText)
        }
    }
    private fun modifierToSkill(num: Int): List<Int>{
        return when(num){
            0-> listOf(3)
            1-> listOf(0, 15, 16)
            2-> emptyList()
            3-> listOf(2, 5, 8, 10, 14)
            4-> listOf(1, 6, 9, 11, 17)
            5-> listOf(4, 7, 12, 13)
            else-> emptyList()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> 0
            1 -> 1
            else -> 2
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view1: View by lazy {
            LayoutInflater.from(parent.context).inflate(R.layout.edit_stats, parent, false)
        }
        val view2: View by lazy {
            LayoutInflater.from(parent.context).inflate(R.layout.edit_saves, parent, false)
        }
        val view3: View by lazy {
            LayoutInflater.from(parent.context).inflate(R.layout.edit_skills, parent, false)
        }
        return when(viewType){
            0-> ViewHolder(view1)
            1-> ViewHolder(view2)
            else -> ViewHolder(view3)
        }

    }
    override fun getItemCount() = 20
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun checkStats(): Boolean{
            val stats = viewModel.stats.value!!
            for(i in stats){
                if (i == null) return false
            }
            return true
        }
        fun compareStats(): Boolean {
            val stats = viewModel.stats.value?.toList()
            val saves = viewModel.saves.value?.toList()
            val skills = viewModel.skills.value?.toList()

            return !(stats == compareStatsList && saves == compareSavesList && skills == compareSkillsList)
        }
        fun checkBtn(){
            if(nextBtn != null){
                nextBtn.isEnabled = if(compare) (compareStats() && checkStats()) else checkStats()
            }
        }

        when(getItemViewType(position)){
            0->{
                val stats = arrayListOf(
                    holder.statsStrength,
                    holder.statsDexterity,
                    holder.statsConstitution,
                    holder.statsIntelligence,
                    holder.statsWisdom,
                    holder.statsCharisma
                )
                for( (i, v) in stats.withIndex()){
                    val value = viewModel.stats.value?.get(i)
                    if(value == 0 || value == null) continue
                    else try{
                        v.setText(value.toString())
                    }
                    catch (e: IllegalStateException){
                        Log.e("EditSkillsListAdapter", e.message?: "IllegalStateException")
                    }
                }

                holder.statsStrengthMod.text = viewModel.modifiers.value?.get(0)?.toString() ?: ""
                holder.statsDexterityMod.text = viewModel.modifiers.value?.get(1)?.toString() ?: ""
                holder.statsConstitutionMod.text = viewModel.modifiers.value?.get(2)?.toString() ?: ""
                holder.statsIntelligenceMod.text = viewModel.modifiers.value?.get(3)?.toString() ?: ""
                holder.statsWisdomMod.text = viewModel.modifiers.value?.get(4)?.toString() ?: ""
                holder.statsCharismaMod.text = viewModel.modifiers.value?.get(5)?.toString() ?: ""

                holder.statsStrength.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(0, null)
                        viewModel.modifiers.value?.set(0, null)
                        holder.statsStrengthMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(0, stat)
                        viewModel.modifiers.value?.set(0, modifier)
                        holder.statsStrengthMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(0)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
                holder.statsDexterity.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(1, null)
                        viewModel.modifiers.value?.set(1, null)
                        holder.statsDexterityMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(1, stat)
                        viewModel.modifiers.value?.set(1, modifier)
                        holder.statsDexterityMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(1)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
                holder.statsConstitution.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(2, null)
                        viewModel.modifiers.value?.set(2, null)
                        holder.statsConstitutionMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(2, stat)
                        viewModel.modifiers.value?.set(2, modifier)
                        holder.statsConstitutionMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(2)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
                holder.statsIntelligence.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(3, null)
                        viewModel.modifiers.value?.set(3, null)
                        holder.statsIntelligenceMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(3, stat)
                        viewModel.modifiers.value?.set(3, modifier)
                        holder.statsIntelligenceMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(3)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
                holder.statsWisdom.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(4, null)
                        viewModel.modifiers.value?.set(4, null)
                        holder.statsWisdomMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(4, stat)
                        viewModel.modifiers.value?.set(4, modifier)
                        holder.statsWisdomMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(4)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
                holder.statsCharisma.addTextChangedListener {
                    if(it.isNullOrBlank()){
                        viewModel.stats.value?.set(5, null)
                        viewModel.modifiers.value?.set(5, null)
                        holder.statsCharismaMod.text = ""
                    }
                    else{
                        val stat = it.toInt()
                        val modifier = (stat-10)/2
                        viewModel.stats.value?.set(5, stat)
                        viewModel.modifiers.value?.set(5, modifier)
                        holder.statsCharismaMod.text = modifier.toString()
                    }

                    notifyItemChanged(1)
                    for(i in modifierToSkill(5)){
                        notifyItemChanged(i+2)
                    }
                    checkBtn()
                }
            }
            1->{
                // init
                holder.savesStrengthBtn.isChecked = viewModel.saves.value?.get(0) ?: false
                holder.savesDexterityBtn.isChecked = viewModel.saves.value?.get(1) ?: false
                holder.savesConstitutionBtn.isChecked = viewModel.saves.value?.get(2) ?: false
                holder.savesIntelligenceBtn.isChecked = viewModel.saves.value?.get(3) ?: false
                holder.savesWisdomBtn.isChecked = viewModel.saves.value?.get(4) ?: false
                holder.savesCharismaBtn.isChecked = viewModel.saves.value?.get(5) ?: false

                val saves = IntArray(6)
                for(i in saves.indices){
                    saves[i] = (viewModel.modifiers.value?.get(i)?:0)+(profBonus * viewModel.saves.value?.get(i).toInt())
                }
                holder.savesStrength.text = saves[0].toString()
                holder.savesDexterity.text = saves[1].toString()
                holder.savesConstitution.text = saves[2].toString()
                holder.savesIntelligence.text = saves[3].toString()
                holder.savesWisdom.text = saves[4].toString()
                holder.savesCharisma.text = saves[5].toString()
                // ---------------------------------------------
                holder.savesStrengthBtn.setOnClickListener{
                    val isChecked = holder.savesStrengthBtn.isChecked
                    viewModel.saves.value?.set(0, isChecked)
                    val text = (viewModel.modifiers.value?.get(0)?:0)+(profBonus*isChecked.toInt())
                    holder.savesStrength.text = text.toString()
                    checkBtn()
                }
                holder.savesDexterityBtn.setOnClickListener{
                    val isChecked = holder.savesDexterityBtn.isChecked
                    viewModel.saves.value?.set(1, isChecked)
                    val text = (viewModel.modifiers.value?.get(1)?:0)+(profBonus*isChecked.toInt())
                    holder.savesDexterity.text = text.toString()
                    checkBtn()
                }
                holder.savesConstitutionBtn.setOnClickListener{
                    val isChecked = holder.savesConstitutionBtn.isChecked
                    viewModel.saves.value?.set(2, isChecked)
                    val text = (viewModel.modifiers.value?.get(2)?:0)+(profBonus*isChecked.toInt())
                    holder.savesConstitution.text = text.toString()
                    checkBtn()
                }
                holder.savesIntelligenceBtn.setOnClickListener{
                    val isChecked = holder.savesIntelligenceBtn.isChecked
                    viewModel.saves.value?.set(3, isChecked)
                    val text = (viewModel.modifiers.value?.get(3)?:0)+(profBonus*isChecked.toInt())
                    holder.savesIntelligence.text = text.toString()
                    checkBtn()
                }
                holder.savesWisdomBtn.setOnClickListener{
                    val isChecked = holder.savesWisdomBtn.isChecked
                    viewModel.saves.value?.set(4, isChecked)
                    val text = (viewModel.modifiers.value?.get(4)?:0)+(profBonus*isChecked.toInt())
                    holder.savesWisdom.text = text.toString()
                    checkBtn()
                }
                holder.savesCharismaBtn.setOnClickListener{
                    val isChecked = holder.savesCharismaBtn.isChecked
                    viewModel.saves.value?.set(5, isChecked)
                    val text = (viewModel.modifiers.value?.get(5)?:0)+(profBonus*isChecked.toInt())
                    holder.savesCharisma.text = text.toString()
                    checkBtn()
                }
            }
            else->{
                // init
                val pos = position-2

                fun getSkillBonus(): Int{
                    val modifier = viewModel.modifiers.value?.get(numToSkill(pos))?: 0
                    val isChecked = viewModel.skills.value?.get(pos)?.toInt()?: 0
                    return modifier + (isChecked * profBonus)
                }
                holder.skillModifier.text = numToModifier(pos)
                holder.skillName.text = getSkillName(pos)
                holder.skillBonus.text = getSkillBonus().toString()
                holder.skillWithBonus.isChecked = viewModel.skills.value?.get(pos) ?: false
                //----------------------------------------------------------
                holder.skillWithBonus.setOnClickListener {
                    val isChecked = holder.skillWithBonus.isChecked
                    viewModel.skills.value?.set(pos, isChecked)

                    holder.skillBonus.text = getSkillBonus().toString()
                    checkBtn()
                }
            }
        }
    }
}