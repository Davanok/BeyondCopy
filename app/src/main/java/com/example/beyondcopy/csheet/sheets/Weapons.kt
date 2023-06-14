package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.adapters.WeaponsListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.WeaponsDataBase
import com.example.beyondcopy.databinding.AddNewWeaponDialogBinding
import com.example.beyondcopy.databinding.FragmentWeaponsBinding
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class Weapons : Fragment() {
    private lateinit var binding: FragmentWeaponsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeaponsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val characterId = requireArguments().getLong("characterId")

        val weapons = dataViewModel.weapons.value?: emptyArray()

        val adapter = WeaponsListAdapter(weapons, dataViewModel, characterId)
        binding.weaponsListLayout.weaponsList.adapter = adapter

        binding.weaponsListLayout.addWeaponBtn.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val tBinding = AddNewWeaponDialogBinding.inflate(layoutInflater)

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
                val name = tBinding.newWeaponName.text.toString()
                val count = tBinding.newWeaponCount.text.toInt()
                val damage = tBinding.newWeaponDamage.text.toString()
                val bonus = tBinding.newWeaponATKBonus.text.toInt()
                val damageType = tBinding.newWeaponDamageType.text.toString()
                val des = tBinding.newWeaponDescription.text.toString()

                lifecycleScope.launch {
                    val weaponId = dataViewModel.insertCharacterWeapon(characterId, name, bonus, damage, damageType, des, count)
                    val weapon = WeaponsDataBase(weaponId, characterId, name, bonus, damage, damageType, des, count)
                    adapter.insertWeapon(weapon)
                }
                dialog.dismiss()
            }
            tBinding.buttonPad.buttonPadCancel.setOnClickListener{ dialog.dismiss()}
            dialog.setContentView(tBinding.root)
            dialog.show()
        }

    }
}