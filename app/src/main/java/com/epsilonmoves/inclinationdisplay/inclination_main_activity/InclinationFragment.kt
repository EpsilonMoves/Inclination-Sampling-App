package com.epsilonmoves.inclinationdisplay.inclination_main_activity


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epsilonmoves.inclinationdisplay.R
import com.epsilonmoves.inclinationdisplay.utils.FACING_DOWN
import com.epsilonmoves.inclinationdisplay.utils.FACING_UP
import com.epsilonmoves.inclinationdisplay.utils.FACING_USER
import kotlinx.android.synthetic.main.fragment_inclination.*


/**
 * A simple [Fragment] subclass.
 */
class InclinationFragment : LifecycleFragment() {
    lateinit var mInclinationView: InclinationViewModelInterface

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_inclination, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //the view model
        mInclinationView = ViewModelProviders.of(activity).get(classOfViewModel)

        // observer
        mInclinationView.getInclinationLiveData().observe(this, Observer { t ->

            t?.let {
                // modify display according to the data
                when (t) {
                    FACING_USER -> inclination_fragment_frame.setBackgroundColor(Color.GREEN)
                    FACING_UP -> inclination_fragment_frame.setBackgroundColor(Color.BLUE)
                    FACING_DOWN -> inclination_fragment_frame.setBackgroundColor(Color.RED)
                }
            }

        })

    }
}
