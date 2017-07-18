package com.epsilonmoves.inclinationdisplay.dao

import io.realm.Realm
import io.realm.RealmResults
/**
* class for the realm DB queries
* */

class RealmQueries: DaoReadInterface, DaoWriteInterface {
    val realm: Realm = Realm.getDefaultInstance()

    override fun insertFacingObjToDb(facing: String, timeStamp: Long, recordId: Int) {
        val deviceFacing = DeviceFacing()
        deviceFacing.id = recordId
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