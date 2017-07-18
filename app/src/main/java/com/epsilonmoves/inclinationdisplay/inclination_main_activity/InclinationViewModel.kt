package com.epsilonmoves.inclinationdisplay.inclination_main_activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.epsilonmoves.inclinationdisplay.Application.MyApp
import com.epsilonmoves.inclinationdisplay.dao.DeviceFacing
import com.epsilonmoves.inclinationdisplay.dao.RealmQueries
import com.epsilonmoves.inclinationdisplay.service.InclinationSamplingService
import com.epsilonmoves.inclinationdisplay.utils.FACING_USER
import io.realm.RealmResults


class InclinationViewModel : ViewModel(), InclinationViewModelInterface {


    var inclinationData = MutableLiveData<String>()

    var results: RealmResults<DeviceFacing>
    val queries: RealmQueries = RealmQueries()

    init {
        // default value for inclinationData
        inclinationData.value = FACING_USER
        // start the service
        val serviceIntent = Intent(MyApp.instance, InclinationSamplingService::class.java)
        MyApp.instance.startService(serviceIntent)
        // realm results
        results = queries.allResults()!!
        results.addChangeListener { _ -> setInclinationLiveData(queries.findNewest(results)) }
    }

    override fun getInclinationLiveData(): MutableLiveData<String> {
        return inclinationData
    }

    override fun setInclinationLiveData(newText: String) {
        inclinationData.value = newText
    }
}