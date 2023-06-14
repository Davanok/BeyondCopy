package com.example.beyondcopy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ItemsDataBase
import com.example.beyondcopy.databinding.AddNewItemDialogBinding
import com.example.beyondcopy.removeAt
import com.example.beyondcopy.toInt
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class ItemsListAdapter(
    private var items: Array<ItemsDataBase>,
    private val viewModel: DataBaseViewModel,
    private val characterId: Long
): RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val itemCard: MaterialCardView by lazy { view.findViewById(R.id.itemCard) }
        val name: TextView by lazy { view.findViewById(R.id.characterItemName) }
        val count: TextView by lazy { view.findViewById(R.id.characterItemCount) }
        val description: TextView by lazy { view.findViewById(R.id.characterItemDescription) }
    }

    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: AddNewItemDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dialog = BottomSheetDialog(parent.context)
        val inflater = LayoutInflater.from(parent.context)
        dialogBinding = AddNewItemDialogBinding.inflate(inflater)

        val view = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    private fun dialogShow (position: Int) {
        val tDialog = dialog
        val tBinding = dialogBinding

        tBinding.buttonPad.buttonPadDelete.visibility = View.VISIBLE

        tBinding.newItemName.setText(items[position].name)
        tBinding.newItemDescription.setText(items[position].description)
        tBinding.newItemCount.setText(items[position].count.toString())

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

            items[position].name = name
            items[position].description = des
            items[position].count = count

            viewModel.updateCharacterItem(characterId, items[position].itemId, name, des, count)

            notifyItemChanged(position)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadDelete.setOnClickListener{
            viewModel.deleteCharacterItem(characterId, items[position].itemId)
            items = items.removeAt(position)
            notifyItemRemoved(position)
            for(i in position until items.size) notifyItemChanged(i)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadCancel.setOnClickListener{ tDialog.dismiss()}
        tDialog.setContentView(tBinding.root)
        tDialog.show()
    }
    fun insertItem(item: ItemsDataBase){
        items += item
        notifyItemInserted(items.size-1)
    }
    fun getItems() = items

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.count.text = items[position].count.toString()
        holder.description.text = items[position].description

        holder.itemCard.setOnClickListener{
            dialogShow(position)
        }
    }

}