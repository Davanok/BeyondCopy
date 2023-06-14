package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.ItemsListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ItemsDataBase
import com.example.beyondcopy.databinding.AddNewItemDialogBinding
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterItemsBinding
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class DefaultCreateCharacterItems : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterItemsBinding
    private lateinit var adapter: ItemsListAdapter
    private lateinit var dataViewModel: DataBaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterItemsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val characterId = requireArguments().getLong("characterId")

        val items = dataViewModel.items.value?: emptyArray()

        adapter = ItemsListAdapter(items, dataViewModel, characterId)

        binding.itemListLayout.addItemBtn.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val tBinding = AddNewItemDialogBinding.inflate(layoutInflater)

            tBinding.newItemName.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newItemDescription.text.isNotBlank() && tBinding.newItemCount.text.isNotBlank())
            }
            tBinding.newItemDescription.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newItemName.text.isNotBlank() && tBinding.newItemCount.text.isNotBlank())
            }
            tBinding.newItemCount.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newItemName.text.isNotBlank() && tBinding.newItemDescription.text.isNotBlank())
            }
            tBinding.buttonPad.buttonPadOK.setOnClickListener {
                val name = tBinding.newItemName.text.toString().trim()
                val des = tBinding.newItemDescription.text.toString().trim()
                val count = tBinding.newItemCount.text.toInt()

                lifecycleScope.launch {
                    val itemId = dataViewModel.insertCharacterItem(characterId, name, des, count)
                    val item = ItemsDataBase(itemId, characterId, name, des, count)
                    adapter.insertItem(item)
                }
                dialog.dismiss()
            }
            tBinding.buttonPad.buttonPadCancel.setOnClickListener{ dialog.dismiss()}
            dialog.setContentView(tBinding.root)
            dialog.show()
        }

        binding.itemListLayout.itemList.adapter = adapter

        binding.defaultNextToWeaponsBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("characterId", characterId)
            findNavController().navigate(R.id.action_defaultCreateCharacterItems_to_defaultCreateCharacterWeapons, bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        dataViewModel.items.value = adapter.getItems()
    }
}