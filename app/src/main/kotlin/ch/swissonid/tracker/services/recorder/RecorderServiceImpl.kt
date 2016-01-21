package ch.swissonid.tracker.services.recorder

import ch.swissonid.tracker.model.Point
import ch.swissonid.tracker.model.Trail
import ch.swissonid.tracker.repository.TrailRepo
import ch.swissonid.tracker.services.tracker.TrackerService


class RecorderServiceImpl(val trailRepo:TrailRepo, val trackerService:TrackerService)
        : RecorderService, TrackerService.OnLocationChangeListener{

    private val mTrailRepo: TrailRepo
    private val mTrackerService: TrackerService
    private val mPoints : MutableList<Point> = arrayListOf()
    private val mTrailBuilder: Trail.TrailBuilder = Trail.TrailBuilder.instance()
    init {
        mTrailRepo = trailRepo
        mTrackerService = trackerService
        mTrackerService.addLocationChangeListener(this)
    }

    override fun startRecording(trailName:String) {
        mTrailBuilder.setName(trailName)
    }

    override fun stopRecording() {
        mTrailBuilder.setPoints(mPoints)
        val trail = mTrailBuilder.createTrail()
        mTrailRepo.save(trail)
    }

    override fun onLocationChange(point: Point) {
        mPoints.add(point)
    }
}