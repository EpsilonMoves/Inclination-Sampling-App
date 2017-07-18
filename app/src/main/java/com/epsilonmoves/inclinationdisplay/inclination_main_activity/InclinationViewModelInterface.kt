package com.epsilonmoves.inclinationdisplay.inclination_main_activity
import android.arch.lifecycle.MutableLiveData

val classOfViewModel = InclinationViewModel::class.java
interface InclinationViewModelInterface {
    fun  getInclinationLiveData(): MutableLiveData<String>
    fun  setInclinationLiveData(newText: String) {}


}