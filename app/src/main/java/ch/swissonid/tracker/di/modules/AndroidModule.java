package ch.swissonid.tracker.di.modules;


import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import ch.swissonid.tracker.TrackerApp;
import ch.swissonid.tracker.services.tracker.TrackerService;
import ch.swissonid.tracker.services.tracker.TrackerServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @Provides @Singleton LocationManager providesLocationManger(final TrackerApp trackerApp){
        return (LocationManager) trackerApp.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides @Singleton TrackerService providesTrackerService(final TrackerApp trackerApp){
        return new TrackerServiceImpl(trackerApp);
    }
}
