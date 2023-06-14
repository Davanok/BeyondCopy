package com.example.beyondcopy.classes

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class Observer(private val button: Button, private vararg var editText: EditText) {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            for (i in editText) {
                if(!extra()){
                    button.isEnabled = false
                    return
                }
                if (i.text.isNullOrBlank()) {
                    button.isEnabled = false
                    return
                }
            }
            button.isEnabled = true
        }
    }
    private val compareTextWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            for ((i, v) in editText.withIndex()){
                if(v.text.toString() != compareList[i]) break
                if(i == editText.lastIndex) {
                    button.isEnabled = false
                    return
                }
            }
            for ((i, v) in editText.withIndex()) {
                if(!extra()){
                    button.isEnabled = false
                    return
                }
                if (v.text.isNullOrBlank()) {
                    button.isEnabled = false
                    return
                }
            }
            button.isEnabled = true
        }
    }

    private var extra: ()-> Boolean = {true}
    private var compareList : List<String> = emptyList()

    fun setObserver(newExtra: ()-> Boolean = {true}) {
        extra = newExtra
        clearObservers()
        for (i in editText) i.addTextChangedListener(textWatcher)
    }
    fun setCompareObserver(newCompareList: List<String>, newExtra: ()-> Boolean = {true}){
        compareList = newCompareList
        extra = newExtra
        clearObservers()
        for(i in editText) i.addTextChangedListener(compareTextWatcher)
    }
    private fun clearObservers() {
        for (i in editText) {
            i.removeTextChangedListener(textWatcher)
            i.removeTextChangedListener(compareTextWatcher)
        }
    }

    companion object{
        fun set(button: Button, vararg editText: EditText, extra: ()->Boolean = {true}){
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
    }
}