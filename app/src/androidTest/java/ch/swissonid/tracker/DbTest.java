package ch.swissonid.tracker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.swissonid.tracker.model.Point;
import ch.swissonid.tracker.model.Trail;
import ch.swissonid.tracker.repository.TrailRepoImpl;

import static org.assertj.core.api.Assertions.*;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class DbTest {

    static Context mMockContext;
    static TrailRepoImpl mTrailRepo;
    private Trail mTrailWithoutPoints;
    private List<Point> mPointList;
    private Trail mTrailWithPoints;
    private Point mPointOne;
    private Point mPointTwo;
    private Point mPointThree;

    @BeforeClass
    public static void setUpDb(){
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");
        mTrailRepo = new TrailRepoImpl(mMockContext);
    }

    @AfterClass
    public static void treadDown(){
        mTrailRepo.eraseEverything();
    }

    @Before
    public void setUp(){
        mTrailWithoutPoints = Trail.TrailBuilder.instance()
                .setLength(10)
                .setName("TestTrail")
                .setPoints(Collections.<Point>emptyList())
                .createTrail();
        mPointList = new ArrayList<>(3);

        mPointOne = new Point(1.0, 1.0);
        mPointTwo = new Point(2.0, 2.0);
        mPointThree = new Point(3.0, 3.0);

        mPointList.add(mPointOne);
        mPointList.add(mPointTwo);
        mPointList.add(mPointThree);

        mTrailWithPoints = Trail.TrailBuilder.instance()
                            .setName("Trail With Points")
                            .setPoints(mPointList)
                            .setLength(20)
                            .createTrail();

    }

    @After
    public void treadDownMethod(){
        mTrailRepo.delete(mTrailWithoutPoints);
        mTrailRepo.delete(mTrailWithPoints);
    }


    @Test
    public void saveTrailToDb(){
        assertPreConditions();

        mTrailRepo.save(mTrailWithoutPoints);

        List<Trail> allTrails = mTrailRepo.getAll();
        assertThat(allTrails.size()).isEqualTo(1);
        assertThat(allTrails.get(0).getId()).isNotEqualTo(0);

        mTrailRepo.save(mTrailWithoutPoints);
        assertThat(mTrailRepo.getAll().size()).isEqualTo(1);
    }

    private void assertPreConditions() {
        assertThat(mTrailRepo.getAll()).isNotNull();
        assertThat(mTrailRepo.getAll()).isEmpty();
    }

    @Test
    public void saveTrailWithPoints(){
        assertPreConditions();
        mTrailRepo.save(mTrailWithPoints);
        List<Trail> allTrails = mTrailRepo.getAll();
        assertThat(allTrails.size()).isEqualTo(1);

        Trail firstTrail = allTrails.get(0);
        assertThat(firstTrail).isNotNull();
        assertThat(firstTrail.getId()).isNotEqualTo(0);
        assertThat(firstTrail.getPoints()).isNotEmpty();
        assertThat(firstTrail.getPoints().size()).isEqualTo(3);
    }

    @Test
    public void deleteTrailWithAllItsPoints(){
        assertPreConditions();

        mTrailRepo.save(mTrailWithPoints);

        assertThat(mTrailWithPoints.exists()).isTrue();
        assertThat(mPointOne.exists()).isTrue();

        mTrailRepo.delete(mTrailWithPoints);

        assertThat(mTrailWithoutPoints.exists()).isFalse();
        assertThat(mPointOne.exists()).isFalse();

    }

    @Test
    public void updateTrail(){
        assertPreConditions();

        mTrailRepo.save(mTrailWithoutPoints);
        assertThat(mTrailWithoutPoints.getId()).isNotZero();

        Trail trail = mTrailRepo.getById(mTrailWithoutPoints.getId());
        assertThat(trail).isNotNull();
        assertThat(trail.getPoints().size()).isZero();
    }



}
