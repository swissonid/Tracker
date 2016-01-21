package ch.swissonid.tracker.model;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ch.swissonid.tracker.repository.TrackerDb;

@Table(database = TrackerDb.class)
public class Point extends BaseModel {

    public Point(){} // Needed by DBFlow

    @IntDef({TYPE_WAY_POINT, TYPE_START_POINT, TYPE_STOP_POINT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PointType {}

    public static final int TYPE_WAY_POINT = 0;
    public static final int TYPE_START_POINT = 1;
    public static final int TYPE_STOP_POINT = 2;


    public Point(final double latitude, final double longitude, @PointType final int type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public Point(final double latitude, final double longitude){
        this(latitude,longitude, TYPE_WAY_POINT);
    }

    @PrimaryKey(autoincrement = true) long id;

    @Column private double longitude;
    
    @Column private double latitude;

    @Column @PointType int type;

    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<Trail> trailForeignKeyContainer;

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @PointType public int getType() {
        return type;
    }

    public void associateTrail(final Trail trail) {
        trailForeignKeyContainer = FlowManager.getContainerAdapter(Trail.class).toForeignKeyContainer(trail);
    }
}
