package ch.swissonid.tracker.repository;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TrackerDb.NAME, version = TrackerDb.VERSION)
public class TrackerDb {
    public static final String NAME = "TrackerDb";
    public static final int VERSION = 1;
}
