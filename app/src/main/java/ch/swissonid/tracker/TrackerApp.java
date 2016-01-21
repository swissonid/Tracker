package ch.swissonid.tracker;

import com.squareup.leakcanary.LeakCanary;

import android.app.Application;
import android.location.LocationManager;

import javax.inject.Inject;

import ch.swissonid.tracker.di.components.AndroidComponent;
import ch.swissonid.tracker.di.components.AppComponent;
import ch.swissonid.tracker.repository.TrailRepo;
import ch.swissonid.tracker.repository.TrailRepoImpl;
import ch.swissonid.tracker.service.ServiceFactory;

public class TrackerApp extends Application {

    private AppComponent mAppComponent;
    private AndroidComponent mAndroidComponent;
    private TrailRepo mTrailRepo;

    @Inject LocationManager mLocationManager;

    @Override public void onCreate(){
        super.onCreate();
        LeakCanary.install(this);
        initInjector();
        mTrailRepo = new TrailRepoImpl(this);
        new ServiceFactory(this);
    }

    private void initInjector() {
        /*mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mAndroidComponent = DaggerAndroidComponent.builder()
                .appModule(new AppModule(this))
                .androidModule(new AndroidModule())
                .build();*/
    }

    public TrailRepo getTrailRepo(){
        return mTrailRepo;
    }
    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public AndroidComponent getAndroidComponent(){
        return mAndroidComponent;
    }
}
