package com.example.beyondcopy

import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random

fun Boolean?.toInt(): Int{
    return if (this == true) 1
    else 0
}
fun Editable?.toInt(): Int{
    return try{
        this.toString().toInt()
    }
    catch (e: NumberFormatException){
        0
    }
}
fun BooleanArray.toIntArray(): IntArray{
    return this.map { it.toInt() }.toIntArray()
}
inline fun <reified T> Array<T>.removeAt(index: Int): Array<T>{
    var result = emptyArray<T>()

    for (i in this.indices) {
        if (i != index) {
            val newItem = this[i]
            result += newItem
        }
    }
    return result
}
fun randInt(min: Int, max: Int): Int {
    // returns random value from min to max
    return Random().nextInt(max - min + 1) + min
}
fun randInt(max: Int): Int {
    // returns random value from 1 to max
    return Random().nextInt(max) + 1
}
fun setObserver(button: Button, vararg editText: EditText, extra: ()->Boolean = {true}){
    for(i in editText) i.addTextChangedListener {
        if(!extra()) {
            button.isEnabled = false
            return@addTextChangedListener
        }
        for(t in editText){
            if(t.text.isNullOrBlank()) {
                button.isEnabled = false
                return@addTextChangedListener
            }
        }
        button.isEnabled = true
    }
}
inline fun <reified T> List<T>.replace(index: Int, value: T): List<T>{
    val list = this.toTypedArray()
    list[index] = value
    return list.toList()
}
inline fun <reified T> Array<T>.toNullableArray(): Array<T?>{
    val result = arrayOfNulls<T>(this.size)
    for((i, v) in this.withIndex()){
        result[i] = v
    }
    return result
}
inline fun <reified T> Array<T?>.toUnNullableArray(): Array<T>{
    val result: List<T> = this.map { it !!}
    return result.toTypedArray()
}
fun LocalDateTime.toReading(): String{
    return this.format(DateTimeFormatter.ofPattern("HH:mm dd MMM yy"))
}
