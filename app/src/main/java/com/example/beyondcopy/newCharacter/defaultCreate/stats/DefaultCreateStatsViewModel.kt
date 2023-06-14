package com.example.beyondcopy.newCharacter.defaultCreate.stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DefaultCreateStatsViewModel: ViewModel() {
    val stats by lazy { // да
        MutableLiveData(Array<Int?>(6){null})
    }
    val modifiers by lazy { // (stats-10)/2
        MutableLiveData(Array<Int?>(6){null})
    }
    val saves by lazy { // либо 1 либо 0
        MutableLiveData(BooleanArray(6){false})
    }
    val skills by lazy{ // либо 1 либо 0
        MutableLiveData(BooleanArray(18){false})
    }
}