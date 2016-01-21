package ch.swissonid.tracker.di.components;

import javax.inject.Singleton;

import ch.swissonid.tracker.BaseActivity;
import ch.swissonid.tracker.TrackerApp;
import ch.swissonid.tracker.di.modules.AppModule;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseActivity baseActivity);
    TrackerApp appContext();
}
