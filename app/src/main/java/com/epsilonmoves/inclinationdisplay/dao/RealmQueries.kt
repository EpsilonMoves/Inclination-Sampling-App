package com.epsilonmoves.inclinationdisplay.dao

import io.realm.Realm
import io.realm.RealmResults
import java.util.*

/**
* class for the realm DB queries
* */

class RealmQueries: DaoReadInterface, DaoWriteInterface {

    private var RECORD_ID = -1
    private val MAX_RECORDS = 500

    val realm: Realm = Realm.getDefaultInstance()

    override fun insertFacingObjToDb(facing: String) {
        // id for the record
        RECORD_ID++
        RECORD_ID %= MAX_RECORDS

        // create a time stamp
        val calendar = Calendar.getInstance()
        val timeStamp = calendar.timeInMillis

        val deviceFacing = DeviceFacing()
        deviceFacing.id = RECORD_ID
        deviceFacing.timeStamp = timeStamp
        deviceFacing.deviceDirection = facing
        realm.executeTransaction(Realm.Transaction() {
            realm.copyToRealmOrUpdate(deviceFacing)
        })
    }

    override fun allResults(): RealmResults<DeviceFacing>? {
        return realm.where(DeviceFacing::class.java)
                .findAllAsync()
    }

    override fun findNewest(results:RealmResults<DeviceFacing>): String {
        val max: Long = results.max("timeStamp").toLong()

        val result = realm.where(DeviceFacing::class.java)
                .equalTo("timeStamp", max)
                .findFirst()
        return result.deviceDirection
    }

}