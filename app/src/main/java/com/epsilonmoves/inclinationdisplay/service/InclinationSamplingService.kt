package com.epsilonmoves.inclinationdisplay.service

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import com.epsilonmoves.inclinationdisplay.dao.DaoWriteInterface
import com.epsilonmoves.inclinationdisplay.dao.RealmQueries
import com.epsilonmoves.inclinationdisplay.utils.FACING_DOWN
import com.epsilonmoves.inclinationdisplay.utils.FACING_UP
import com.epsilonmoves.inclinationdisplay.utils.FACING_USER

/**
 * Intent service works on a different thread
 * */
class InclinationSamplingService : IntentService(""), SensorEventListener {



    private val FACE_UP_INCLINATION = 45
    private val FACE_DOWN_INCLINATION = 125
    private var MATRIX_SIZE = 9
    private val SAMPLE_DELAY = 300
    private val ROTATION_MATRIX_CELL = 8
    private val TAG = InclinationSamplingService::class.java.name.toString()
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mMagnetic: Sensor
    // Realm interface
    private val realWrite: DaoWriteInterface = RealmQueries()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        onHandleIntent()
        return Service.START_NOT_STICKY
    }

    override fun onHandleIntent(p0: Intent?) {
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    var rotationMatrix = FloatArray(MATRIX_SIZE)
    var inclinationMatrix = FloatArray(MATRIX_SIZE)
    var accelerometer: FloatArray? = null // values from sensor
    var magnetic: FloatArray? = null // values from sensor
    fun onHandleIntent() {
        // Can't find a device without sensors, so I don't know if an exception will be thrown ...
        try {
            mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            mSensorManager.registerListener(this, mAccelerometer, SAMPLE_DELAY)

            mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            mSensorManager.registerListener(this, mMagnetic, SAMPLE_DELAY)
        } catch (e: Exception) {

        }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometer = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetic = event.values.clone()
        }
        if (accelerometer != null && magnetic != null) {





            SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelerometer, magnetic)
            val inclination = Math.round(Math.toDegrees(Math.acos(rotationMatrix[ROTATION_MATRIX_CELL].toDouble()))).toInt()


            if (inclination < FACE_UP_INCLINATION) {
                realWrite.insertFacingObjToDb(FACING_UP)
            } else if (inclination in FACE_UP_INCLINATION..FACE_DOWN_INCLINATION) {
                realWrite.insertFacingObjToDb(FACING_USER)
            } else if (inclination > FACE_DOWN_INCLINATION) {
                realWrite.insertFacingObjToDb(FACING_DOWN)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregister the listener
        mSensorManager.unregisterListener(this, mAccelerometer)
        mSensorManager.unregisterListener(this, mMagnetic)
    }
}