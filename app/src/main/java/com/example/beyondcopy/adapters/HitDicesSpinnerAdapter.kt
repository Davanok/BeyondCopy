package com.example.beyondcopy.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.beyondcopy.classes.Dice
import com.google.android.material.color.MaterialColors

class HitDicesSpinnerAdapter(context: Context, items: List<Dice>):
    ArrayAdapter<Dice>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView = ImageView(context)
        imageView.setImageResource(getItem(position)?.drawableId?:0)
        imageView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.setColorFilter(
            MaterialColors.getColor( imageView, androidx.appcompat.R.attr.colorAccent )
        )
        return imageView
    }
}