package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.WeaponsListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.WeaponsDataBase
import com.example.beyondcopy.databinding.AddNewWeaponDialogBinding
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterWeaponsBinding
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class DefaultCreateCharacterWeapons : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterWeaponsBinding
    private lateinit var adapter: WeaponsListAdapter
    private lateinit var dataViewModel: DataBaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterWeaponsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val characterId = requireArguments().getLong("characterId")

        val weapons = dataViewModel.weapons.value?: emptyArray()

        adapter = WeaponsListAdapter(weapons, dataViewModel, characterId)

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
                val name = tBinding.newWeaponName.text.toString().trim()
                val count = tBinding.newWeaponCount.text.toInt()
                val damage = tBinding.newWeaponDamage.text.toString().trim()
                val bonus = tBinding.newWeaponATKBonus.text.toInt()
                val damageType = tBinding.newWeaponDamageType.text.toString().trim()
                val des = tBinding.newWeaponDescription.text.toString().trim()

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

        binding.weaponsListLayout.weaponsList.adapter = adapter

        binding.defaultNextToFeaturesBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("characterId", characterId)

            findNavController().navigate(R.id.action_defaultCreateCharacterWeapons_to_defaultCreateCharacterFeatures, bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        dataViewModel.weapons.value = adapter.getWeapons()
    }
}