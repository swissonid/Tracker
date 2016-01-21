package ch.swissonid.tracker.repository;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;


import org.jetbrains.annotations.NotNull;

import android.content.Context;

import java.util.List;

import ch.swissonid.tracker.model.Point;
import ch.swissonid.tracker.model.Trail;
import ch.swissonid.tracker.model.Trail_Table;


public class TrailRepoImpl implements TrailRepo {

    @Override public void save(@NotNull final Trail trail) {
        trail.save();
        if(!trail.hasPoints()) return;
        List<Point> list = trail.getPoints();
        for (Point point : list) {
            point.associateTrail(trail);
            point.save();
        }
    }

    public TrailRepoImpl(final Context context){
        FlowManager.init(context);
    }

    @NotNull @Override public Trail getById(@NotNull final long id) {
        return SQLite.select()
                .from(Trail.class)
                .where(Trail_Table.id.eq(id))
                .querySingle();
    }

    @NotNull @Override public List<Trail> getAll() {
        return SQLite.select()
                .from(Trail.class)
                .orderBy(Trail_Table.name, true)
                .queryList();
    }

    @Override public void eraseEverything() {
        Delete.tables(Trail.class, Point.class);
    }

    @Override public void delete(@NotNull final Trail trail) {
        trail.delete();
    }
}
