package ch.swissonid.tracker.service;

import ch.swissonid.tracker.TrackerApp;
import ch.swissonid.tracker.services.recorder.RecorderService;
import ch.swissonid.tracker.services.recorder.RecorderServiceImpl;
import ch.swissonid.tracker.services.tracker.TrackerService;
import ch.swissonid.tracker.services.tracker.TrackerServiceImpl;

//TODO move it to dagger
public class ServiceFactory {

    private static ServiceFactory mFactory;
    private final TrackerApp mApp;

    public ServiceFactory(final TrackerApp trackerApp){
        mApp = trackerApp;
        mFactory = this;
    }

    public static ServiceFactory Instance(){
        return mFactory;
    }

    public TrackerService getTrackerService(){
        return new TrackerServiceImpl(mApp);
    }
    public RecorderService getRecorderService(){
        return new RecorderServiceImpl(mApp.getTrailRepo(),getTrackerService());
    }
}
