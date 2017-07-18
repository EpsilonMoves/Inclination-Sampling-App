package com.epsilonmoves.inclinationdisplay.inclination_main_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.epsilonmoves.inclinationdisplay.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // the fragment
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, InclinationFragment()).commit()
    }
}
