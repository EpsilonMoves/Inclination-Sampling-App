package com.epsilonmoves.inclinationdisplay.dao

import io.realm.RealmResults

interface DaoReadInterface {

    fun allResults(): RealmResults<DeviceFacing>?
    fun findNewest(results:RealmResults<DeviceFacing>): String
}