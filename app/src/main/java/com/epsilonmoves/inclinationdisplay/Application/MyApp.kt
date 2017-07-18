package com.epsilonmoves.inclinationdisplay.Application

import android.app.Application
import io.realm.Realm

class MyApp:Application() {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)

        instance = this
    }
}