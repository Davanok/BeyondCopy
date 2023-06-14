package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.NotesListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.NotesDataBase
import com.example.beyondcopy.databinding.AddNewNoteDialogBinding
import com.example.beyondcopy.databinding.FragmentNotesBinding
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class Notes : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val characterId = requireArguments().getLong("characterId")
        val dataBaseViewModel by activityViewModels<DataBaseViewModel>()
        val notes = dataBaseViewModel.notes.value?.toMutableList() ?: mutableListOf()

        val adapter = NotesListAdapter(notes, characterId, dataBaseViewModel, 0)

        binding.notesRecyclerView.adapter = adapter

        val sortingArray = resources.getStringArray(R.array.sortNotes)
        binding.sortNotesBy.setSimpleItems(sortingArray)
        var selectedSort = 0
        adapter.sortPos = selectedSort
        binding.sortNotesBy.setText(sortingArray[selectedSort], false)
        binding.sortNotesBy.setOnItemClickListener { _, _, i, _ ->
            selectedSort = i
            adapter.sortPos = i
        }

        binding.searchNote.addTextChangedListener {
            adapter.search(it.toString())
            binding.addNoteBtn.isEnabled = it.isNullOrBlank()
        }

        binding.addNoteBtn.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val dBinding = AddNewNoteDialogBinding.inflate(layoutInflater)

            setObserver(
                dBinding.buttonPad.buttonPadOK,
                dBinding.newNoteName,
                dBinding.newNotePriority,
                dBinding.newNoteText
            )

            dBinding.buttonPad.buttonPadCancel.setOnClickListener { dialog.dismiss() }
            dBinding.buttonPad.buttonPadOK.setOnClickListener {
                dialog.dismiss()
                val name = dBinding.newNoteName.text.toString()
                val text = dBinding.newNoteText.text.toString()
                val priority = dBinding.newNotePriority.text.toInt()
                val dateTime = LocalDateTime.now()
                val completed = dBinding.completedNoteSwitch.isChecked

                lifecycleScope.launch {
                    val noteId = dataBaseViewModel.insertCharacterNote(
                        characterId,
                        name,
                        text,
                        priority,
                        dateTime,
                        completed)
                    val note = NotesDataBase(noteId, characterId, name, text, priority, dateTime, completed)
                    requireActivity().runOnUiThread {
                        adapter.addNote(note)
                    }
                }
            }

            dialog.setContentView(dBinding.root)
            dialog.show()
        }
    }
}