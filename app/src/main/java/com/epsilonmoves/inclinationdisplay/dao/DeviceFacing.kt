package com.epsilonmoves.inclinationdisplay.dao

import com.epsilonmoves.inclinationdisplay.utils.FACING_USER
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
* Realm DB object
* */
open class DeviceFacing: RealmObject() {

    @PrimaryKey
    var id:Int = 0
    var timeStamp:Long=0
    var deviceDirection:String = FACING_USER
}