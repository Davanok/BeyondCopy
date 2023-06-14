package com.example.beyondcopy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.NotesDataBase
import com.example.beyondcopy.databinding.AddNewNoteDialogBinding
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt
import com.example.beyondcopy.toReading
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class NotesListAdapter(
    private var notes: MutableList<NotesDataBase>,
    private val characterId: Long,
    private val viewModel: DataBaseViewModel,
    sortPos: Int
    ) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>(){
    private var filteredList = notes
    override fun getItemCount() = filteredList.size

    var sortPos: Int = sortPos
        set(value) {
            field = value
            updateList()
        }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card: MaterialCardView by lazy { view.findViewById(R.id.noteCard) }
        val name: TextView by lazy { view.findViewById(R.id.noteName) }
        val text: TextView by lazy { view.findViewById(R.id.noteText) }
        val dateTime: TextView by lazy { view.findViewById(R.id.noteDateTime) }
        val completed: CheckBox by lazy { view.findViewById(R.id.completedNote) }
    }

    private lateinit var dialog: BottomSheetDialog
    private lateinit var dBinding: AddNewNoteDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        dialog = BottomSheetDialog(parent.context)
        dBinding = AddNewNoteDialogBinding.inflate(inflater)

        val view = inflater.inflate(R.layout.note_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = filteredList[position]

        holder.name.text = note.name
        holder.text.text = note.text
        holder.dateTime.text = note.date.toReading()

        holder.completed.isChecked = note.completed
        holder.completed.isEnabled = !note.completed

        holder.completed.setOnClickListener{
            val isChecked = holder.completed.isChecked
            viewModel.updateCharacterNote(
                characterId, note.noteId, note.name, note.text, note.priority, note.date, isChecked
            )
            filteredList[position].completed = isChecked
            it.isEnabled = !isChecked
            updateList()
        }
        holder.card.setOnClickListener{
            dBinding.buttonPad.buttonPadDelete.visibility = View.VISIBLE

            dBinding.newNoteName.setText(note.name)
            dBinding.newNoteText.setText(note.text)
            dBinding.newNotePriority.setText(note.priority.toString())
            dBinding.completedNoteSwitch.isChecked = note.completed

            dBinding.buttonPad.buttonPadOK.isEnabled = true
            setObserver(
                dBinding.buttonPad.buttonPadOK,
                dBinding.newNoteName,
                dBinding.newNotePriority,
                dBinding.newNoteText
            )

            dBinding.buttonPad.buttonPadCancel.setOnClickListener { dialog.dismiss() }
            dBinding.buttonPad.buttonPadDelete.setOnClickListener {
                dialog.dismiss()
                viewModel.deleteCharacterNote(characterId, filteredList[position].noteId)
                notes.remove(filteredList[position])
                filteredList.removeAt(position)
                viewModel.notes.value = notes.toTypedArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, filteredList.size - position)
            }
            dBinding.buttonPad.buttonPadOK.setOnClickListener {
                dialog.dismiss()
                val name = dBinding.newNoteName.text.toString()
                val text = dBinding.newNoteText.text.toString()
                val priority = dBinding.newNotePriority.text.toInt()
                val completed = dBinding.completedNoteSwitch.isChecked
                viewModel.updateCharacterNote(characterId, note.noteId,
                    name,
                    text,
                    priority,
                    note.date,
                    completed)
                filteredList[position].let {
                    it.name = name
                    it.text = text
                    it.priority = priority
                    it.completed = completed
                }

                notifyItemChanged(position)
                updateList()
            }

            dialog.setContentView(dBinding.root)
            dialog.show()
        }
    }

    private fun updateList(){
        val array1 = notes.filter { !it.completed }
        val array2 = notes.filter { it.completed }

        val nameComparator = Comparator<NotesDataBase>{ x, y -> x.name.compareTo(y.name) }
        val priorityComparator = Comparator<NotesDataBase>{ x, y -> y.priority.compareTo(x.priority) }
        val dateComparator = Comparator<NotesDataBase>{ x, y -> y.date.compareTo(x.date) }

        val sorter = when(sortPos){
            0 -> nameComparator
            1 -> priorityComparator
            2 -> dateComparator
            else -> nameComparator
        }

        val filteredArray1 = array1.sortedWith(sorter)
        val filteredArray2 = array2.sortedWith(sorter)

        val newList = filteredArray1 + filteredArray2
        filteredList = newList.toMutableList()

        notifyItemRangeChanged(0, filteredList.size)
    }

    fun addNote(note: NotesDataBase){
        notes.add(note)
        notifyItemInserted(notes.lastIndex)
        updateList()
    }

    fun search(text: String){
        var newNotes = mutableListOf<NotesDataBase>()
        var removedIndices = emptyArray<Int>()
        if(text.isBlank()) newNotes = notes
        else {
            for ((i, v) in notes.withIndex()) {
                if (text in v.name || text in v.text) newNotes.add(v)
                else removedIndices += i
            }
        }
        filteredList = newNotes
        notifyDataSetChanged()
    }
}