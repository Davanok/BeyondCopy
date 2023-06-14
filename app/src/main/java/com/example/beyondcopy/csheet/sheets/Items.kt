package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.adapters.ItemsListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ItemsDataBase
import com.example.beyondcopy.databinding.AddNewItemDialogBinding
import com.example.beyondcopy.databinding.FragmentItemsBinding
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class Items : Fragment() {
    private lateinit var binding: FragmentItemsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val characterId = requireArguments().getLong("characterId")
        val items = dataViewModel.items.value?: emptyArray()
        val adapter = ItemsListAdapter(items, dataViewModel, characterId)
        binding.itemListLayout.itemList.adapter = adapter

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
    }
}
