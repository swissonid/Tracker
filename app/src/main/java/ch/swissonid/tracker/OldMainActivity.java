package ch.swissonid.tracker;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.jetbrains.annotations.NotNull;

import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import ch.swissonid.tracker.model.Point;
import ch.swissonid.tracker.service.ServiceFactory;
import ch.swissonid.tracker.services.recorder.RecorderService;
import ch.swissonid.tracker.services.tracker.TrackerService;

public class OldMainActivity extends BaseActivity implements OnMapReadyCallback, TrackerService.OnLocationChangeListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 23;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String DIALOG_ERROR = "dialog_error";
    private boolean mResolvingError = false;

    @Inject LocationManager mLocationManager;

    Button mStart, mStop, mShow;
    private GoogleMap mGoogleMap;
    private Polyline mLine;
    private ArrayList<LatLng> mPoints;
    private Marker mStartMarker;
    private TrackerService mTrackerService;
    private RecorderService mRecorderService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainold);
      //  getAndroidComponent().inject(this);
        initViews();
        mRecorderService = ServiceFactory.Instance().getRecorderService();
    }


    protected void onStop(){
        super.onStop();
    }

    private void initViews() {
        mShow = (Button) findViewById(R.id.show);
        mStart = (Button) findViewById(R.id.start);
        mStop = (Button) findViewById(R.id.stop);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                mRecorderService.startRecording("Test");
            }
        });

        mStop.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(final View v) {
                mRecorderService.stopRecording();
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addNewMessage("First test Message");
    }



    private void addNewMessage(final String msg){
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }


    @Override public void onMapReady(final GoogleMap googleMap) {
        mGoogleMap = googleMap;
       /* if(mLastLocation == null) return;
        addStartMarker(mLastLocation);*/
    }

    private void addStartMarker(final Location location) {
        LatLng latLng = getLatLng(location);
        mStartMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Your last postion"));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    private void addStartMarker(final Point location) {
        LatLng latLng = getLatLng(location);
        mStartMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Your last postion"));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    @NonNull private LatLng getLatLng(final Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @NonNull private LatLng getLatLng(final Point location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override public void onLocationChange(@NotNull final Point point) {
        if(mStartMarker == null) addStartMarker(point);
        if(mLine == null){
            mLine = mGoogleMap.addPolyline(new PolylineOptions().add(getLatLng(point)));
        }
        if(mPoints == null || mPoints.isEmpty()){
            mPoints = new ArrayList<>(1);
        }
        mPoints.add(getLatLng(point));
        mLine.setPoints(mPoints);
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((OldMainActivity) getActivity()).onDialogDismissed();
        }
    }

    public void onDialogDismissed() {
        mResolvingError = false;
    }
}
