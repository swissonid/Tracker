package ch.swissonid.tracker.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Collections;
import java.util.List;

import ch.swissonid.tracker.repository.TrackerDb;

@ModelContainer
@Table(database = TrackerDb.class)
public class Trail extends BaseModel{

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;

    @Column
    double length;

    List<Point> points;

    public Trail(){} //Needed by DBFlow

    public Trail(final long id, final String name, final double length, final List<Point> points) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.points = points;
    }

    public double getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }


    @OneToMany(methods =  {OneToMany.Method.ALL}, variableName = "points")
    public List<Point> getPoints(){
        if(!hasPoints()){
            points = SQLite.select()
                    .from(Point.class)
                    .where(Point_Table.trailForeignKeyContainer_id.eq(id))
                    .queryList();
        }
        if(points == null) points = Collections.emptyList();
        return points;
    }

    public boolean hasPoints(){
        return !(points == null || points.isEmpty());
    }

    public static class TrailBuilder {
        private long mId;
        private String mName;
        private double mLength;
        private List<Point> mPoints;

        public TrailBuilder setId(final long id) {
            mId = id;
            return this;
        }

        public TrailBuilder setName(final String name) {
            mName = name;
            return this;
        }

        public TrailBuilder setLength(final double length) {
            mLength = length;
            return this;
        }

        public TrailBuilder setPoints(final List<Point> points) {
            mPoints = points;
            return this;
        }

        public static TrailBuilder instance() { return new TrailBuilder();}
        public Trail createTrail() {
            return new Trail(mId, mName, mLength, mPoints);
        }
    }
}
