package ch.swissonid.tracker.services.tracker

import ch.swissonid.tracker.model.Point


interface TrackerService {
    fun startTracking()
    fun stopTracking()
    fun getLastPosition(): Point
    fun addLocationChangeListener(listener: OnLocationChangeListener)
    fun removeLocationChangeListener(listener: OnLocationChangeListener)
    fun addErrorListener(errorListener: OnError)
    fun removeErrorListener(errorListener: OnError)

    interface OnLocationChangeListener{
        fun onLocationChange(point: Point)
    }

    interface OnError{
        fun onError(errorCode: Int)
    }
}