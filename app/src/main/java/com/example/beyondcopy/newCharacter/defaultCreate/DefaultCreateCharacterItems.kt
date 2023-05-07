package com.example.beyondcopy.newCharacter.defaultCreate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.AddNewItemBinding
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterItemsBinding

class DefaultCreateCharacterItems : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterItemsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterItemsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]
        val characterId = requireArguments().getLong("characterId")

        binding.addItemBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(context).create()
            val dialogBinding = AddNewItemBinding.inflate(layoutInflater)

            dialog.setTitle(R.string.addItem)
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)){_,_-> dialog.dismiss() }
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.OK)){_,_->
                if(dialogBinding.newItemName.text.isBlank() || dialogBinding.newItemDescription.text.isBlank()){
                    Toast.makeText(context, R.string.enterParams, Toast.LENGTH_SHORT).show()
                } else {
                    dataViewModel.insertCharacterItem(
                        characterId,
                        dialogBinding.newItemName.text.toString(),
                        dialogBinding.newItemDescription.text.toString()
                    )
                }
            }
            dialog.setView(dialogBinding.root)
            dialog.show()
        }
    }
}