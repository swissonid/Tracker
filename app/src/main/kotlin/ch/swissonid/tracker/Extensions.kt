package ch.swissonid.tracker

import android.location.Location
import ch.swissonid.tracker.model.Point
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng(): LatLng{
    return LatLng(this.latitude, this.longitude)
}

fun Location.toPoint(): Point{
    return Point(this.latitude, this.longitude)
}



