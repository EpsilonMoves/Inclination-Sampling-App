package com.epsilonmoves.inclinationdisplay.dao

interface DaoWriteInterface {

    fun insertFacingObjToDb(facing: String, timeStamp: Long, recordId: Int)
}