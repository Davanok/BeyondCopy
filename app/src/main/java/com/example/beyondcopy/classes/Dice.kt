package com.example.beyondcopy.classes

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.isDigitsOnly
import com.example.beyondcopy.R
import com.example.beyondcopy.randInt

class Dice(private val context: Context, val value: Int){
    val dice: String
        get() = context.getString(R.string.dice, value)

    val random: Int
        get() = randInt(value)
    fun random(profBonus: Int, modifier: Int): Int{
        return randInt(value) + profBonus + modifier
    }
    fun random(profBonus: Int, modifier: Int, count: Int): Int{
        var result = 0
        for(i in 0 until count) result += random(profBonus, modifier)
        return result
    }
    private var customDrawableId = false
    var drawableId: Int = 0
        get() =
            if(customDrawableId) field
            else when(value){
                4->R.drawable.d4
                6->R.drawable.d6
                8->R.drawable.d8
                10->R.drawable.d12
                12->R.drawable.d12
                else->R.drawable.d20
            }
        set(value) {
            customDrawableId = true
            field = value
        }
    val drawable: Drawable?
        get() = AppCompatResources.getDrawable(context, drawableId)
    companion object{
        public fun getDice(context: Context, dice: String): Dice{
            Log.d("MyLog", "getDice $dice")
            return if(dice.isDigitsOnly()) Dice(context, dice.toInt())
            else if(dice.length <= 1) Dice(context, 0)
            else getDice(context, dice.substring(1))
        }

    }
}