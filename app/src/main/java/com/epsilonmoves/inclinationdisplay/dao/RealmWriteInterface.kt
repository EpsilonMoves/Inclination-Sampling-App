package com.epsilonmoves.inclinationdisplay.dao

interface RealmWriteInterface {

    fun insertFacingObjToDb(facing: String, timeStamp: Long, recordId: Int)
}