package com.example.beyondcopy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.WeaponsDataBase
import com.example.beyondcopy.databinding.AddNewWeaponDialogBinding
import com.example.beyondcopy.removeAt
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog

class WeaponsListAdapter(
    private var weapons: Array<WeaponsDataBase>,
    private val viewModel: DataBaseViewModel,
    private val characterId: Long
): RecyclerView.Adapter<WeaponsListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView by lazy {view.findViewById(R.id.characterWeaponName)}
        val count: TextView by lazy {view.findViewById(R.id.characterWeaponCount)}
        val damage: TextView by lazy {view.findViewById(R.id.characterWeaponDamage)}
        val bonus: TextView by lazy {view.findViewById(R.id.characterWeaponATKBonus)}
        val damageType: TextView by lazy {view.findViewById(R.id.characterWeaponDamageType)}
        val description: TextView by lazy {view.findViewById(R.id.characterWeaponDescription)}
        val weaponCard: CardView by lazy {view.findViewById(R.id.weaponCard)}
    }

    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: AddNewWeaponDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dialog = BottomSheetDialog(parent.context)
        val inflater = LayoutInflater.from(parent.context)
        dialogBinding = AddNewWeaponDialogBinding.inflate(inflater)

        val view = inflater.inflate(R.layout.weapon_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = weapons.size

    private fun dialogShow (position: Int) {
        val tDialog = dialog
        val tBinding = dialogBinding

        tBinding.buttonPad.buttonPadDelete.visibility = View.VISIBLE

        tBinding.newWeaponName.setText(weapons[position].name)
        tBinding.newWeaponCount.setText(weapons[position].count.toString())
        tBinding.newWeaponDamage.setText(weapons[position].damage)
        tBinding.newWeaponATKBonus.setText(weapons[position].ATKBonus.toString())
        tBinding.newWeaponDamageType.setText(weapons[position].damageType)
        tBinding.newWeaponDescription.setText(weapons[position].description)

        setObserver(
            tBinding.buttonPad.buttonPadOK,

            tBinding.newWeaponName,
            tBinding.newWeaponCount,
            tBinding.newWeaponDamage,
            tBinding.newWeaponATKBonus,
            tBinding.newWeaponDamageType,
            tBinding.newWeaponDescription
        )
        tBinding.buttonPad.buttonPadOK.setOnClickListener {
            val name = tBinding.newWeaponName.text.toString().trim()
            val count = tBinding.newWeaponCount.text.toInt()
            val damage = tBinding.newWeaponDamage.toString().trim()
            val bonus = tBinding.newWeaponATKBonus.text.toInt()
            val damageType = tBinding.newWeaponDamageType.text.toString().trim()
            val des = tBinding.newWeaponDescription.text.toString().trim()

            weapons[position].name = name
            weapons[position].count = count
            weapons[position].damage = damage
            weapons[position].ATKBonus = bonus
            weapons[position].damageType = damageType
            weapons[position].description = des

            viewModel.updateCharacterWeapon(characterId, weapons[position].weaponId, name, bonus, damage, damageType, des, count)

            notifyItemChanged(position)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadDelete.setOnClickListener{
            viewModel.deleteCharacterWeapon(characterId, weapons[position].weaponId)
            weapons = weapons.removeAt(position)
            notifyItemRemoved(position)
            for(i in position until weapons.size) notifyItemChanged(i)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadCancel.setOnClickListener{ tDialog.dismiss()}
        tDialog.setContentView(tBinding.root)
        tDialog.show()
    }
    fun insertWeapon(weapon: WeaponsDataBase){
        weapons += weapon
        notifyItemInserted(weapons.size-1)
    }
    fun getWeapons() = weapons

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = weapons[position].name
        holder.count.text = weapons[position].count.toString()
        holder.damage.text = weapons[position].damage
        holder.bonus.text = weapons[position].ATKBonus.toString()
        holder.damageType.text = weapons[position].damageType
        holder.description.text = weapons[position].description

        holder.weaponCard.setOnClickListener {
            dialogShow(position)
        }
    }
}