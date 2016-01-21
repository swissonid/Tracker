package ch.swissonid.tracker.di.modules;



import javax.inject.Singleton;

import ch.swissonid.tracker.TrackerApp;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final TrackerApp mTrackerApp;
    public AppModule(TrackerApp trackerApp){
        mTrackerApp = trackerApp;
    }

    @Provides @Singleton TrackerApp provideAppContext(){
        return mTrackerApp;
    }
}
