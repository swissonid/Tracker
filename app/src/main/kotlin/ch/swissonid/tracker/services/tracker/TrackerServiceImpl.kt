package ch.swissonid.tracker.services.tracker

import android.location.Location
import android.os.Bundle
import android.util.Log
import ch.swissonid.tracker.TrackerApp
import ch.swissonid.tracker.model.Point
import ch.swissonid.tracker.toPoint
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class TrackerServiceImpl(context : TrackerApp) : TrackerService
                            , GoogleApiClient.ConnectionCallbacks
                            , GoogleApiClient.OnConnectionFailedListener
                            , LocationListener {

    override fun getLastPosition(): Point {
        return getLastLocation().toPoint()
    }

    override fun removeErrorListener(errorListener: TrackerService.OnError) {
        throw UnsupportedOperationException()
    }

    override fun addErrorListener(errorListener: TrackerService.OnError) {
        throw UnsupportedOperationException()
    }


    private val mGoogleApiClient : GoogleApiClient
    private var mLocationRequest: LocationRequest = createLocationRequest()
    private var mLocationListeners : MutableList<TrackerService.OnLocationChangeListener> = arrayListOf()

    private val TAG = TrackerServiceImpl::class.java.name

    init {
        mGoogleApiClient = GoogleApiClient.Builder(context)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build()
    }



    override fun onConnected(bundle: Bundle?) {
        Log.d(TAG, "Successful connected")
        startTracking()
    }

    private fun getLastLocation(): Location {
         return LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    override fun onConnectionSuspended(p0: Int) {
        throw UnsupportedOperationException()
    }

    override fun onConnectionFailed(result: ConnectionResult?) {
        if(result?.hasResolution()!!){
            Log.e(TAG, "Connection failed with solution");
        }else{
            Log.e(TAG, "Connection failed without solution");
        }
    }

    override fun startTracking() {
        if(!mGoogleApiClient.isConnected){
            mGoogleApiClient.connect()
        }else {
               LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this)
        }

    }

    override fun stopTracking() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this)
    }

    override fun onLocationChanged(location: Location?) {
        if(location == null) return
        Log.d(TAG, "NEW LOCATION: "+location.toString())
        val point = location.toPoint()
        mLocationListeners.forEach { it.onLocationChange(point) }
    }

    override fun addLocationChangeListener(listener: TrackerService.OnLocationChangeListener) {
        mLocationListeners.add(listener)
    }

    override fun removeLocationChangeListener(listener: TrackerService.OnLocationChangeListener) {
        mLocationListeners.remove(listener)
    }

    protected fun createLocationRequest() : LocationRequest {
        var request = LocationRequest()
        request.setInterval(5000)
        request.setFastestInterval(1000)
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        return request
    }

}