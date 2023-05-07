package com.example.beyondcopy.newCharacter.defaultCreate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DefaultCreateCharacterViewModel: ViewModel() {
    val stats by lazy { // да
        MutableLiveData(IntArray(6))
    }
    val modifiers by lazy { // (stats-10)/2
        MutableLiveData(IntArray(6))
    }
    val saves by lazy { // либо 1 либо 0
        MutableLiveData(BooleanArray(6){false})
    }
    val skills by lazy{ // либо 1 либо 0
        MutableLiveData(BooleanArray(18){false})
    }
}