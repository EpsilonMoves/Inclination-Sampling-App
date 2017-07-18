package com.epsilonmoves.inclinationdisplay.dao

import io.realm.RealmResults

interface RealmReadInterface {

    fun allResults(): RealmResults<DeviceFacing>?
    fun findNewest(results:RealmResults<DeviceFacing>): String
}