package fr.wildcodeschool.seeknwild.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import java.util.List;
import java.util.Random;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserAdventureSingleton;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class SearchTreasureFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1034;
    private static final int MIN_DISTANCE = 10;
    private static final int DEFAULT_ZOOM = 17;
    private static final int RANDOM_HEADING = 360;
    private static final int RANDOM_DISTANCE = 20;
    private static final int RADIUS_RANDOM_CIRCLE = 250;
    private static final int DISTANCE_USER_BETWEEN_TREASURE = 5;
    private static final int TIME_VIBRATION = 1500;

    private SupportMapFragment mapFragment;
    private SearchTreasureFragment.SearchTreasureListener listener;
    private View view;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mustMove = false;
    private Location mLocation;
    private Treasure treasure;
    private UserAdventure userAdventure;
    private Long idUser;
    private User user;
    private Long idUserAdventure;

    public SearchTreasureFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SearchTreasureListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search_treasure, container, false);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        idUser = user.getIdUser();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        idUserAdventure = userAdventureSingleton.getUserAdventureId();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasure = treasures.get(userAdventure.getCurrentTreasure());

        TextView etDescriptionTreasure = view.findViewById(R.id.etDescriptionTreasure);
        etDescriptionTreasure.setText(treasure.getDescription());

        ImageView treasureImg = view.findViewById(R.id.ivTreasure);
        Glide.with(this).load(treasure.getPictureTreasure()).into(treasureImg);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.stylemap));
                    LatLng latLongTreasure = new LatLng(treasure.getLatTreasure(), treasure.getLongTreasure());
                    final MarkerOptions markerOptions = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.tresor1));
                    markerOptions.position(latLongTreasure);

                    Random r = new Random();
                    int randomHeading = r.nextInt(RANDOM_HEADING);
                    int randomDistance = r.nextInt(RANDOM_DISTANCE);
                    LatLng positionAleatoire = SphericalUtil.computeOffset(latLongTreasure, randomDistance, randomHeading);
                    mMap.addCircle(new CircleOptions()
                            .center(positionAleatoire)
                            .radius(RADIUS_RANDOM_CIRCLE)
                            .strokeColor(getContext().getResources().getColor(R.color.colorPrimaryMaquette))
                            .fillColor(getContext().getResources().getColor(R.color.colorCircleTreasure)));

                    final Button btFoundIt = view.findViewById(R.id.btFoundIt);
                    if (userAdventure.getNbTreasure() >= 4) {
                        btFoundIt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.clear();
                                mMap.addMarker(markerOptions);
                                VolleySingleton.getInstance(getContext()).updateUserAdventure(idUser, idUserAdventure, true,
                                        new Consumer<UserAdventure>() {
                                            @Override
                                            public void accept(UserAdventure userAdventure) {
                                                UserSingleton.getInstance().setUser(user);
                                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                                listener.onFinishAdventureGoRate();

                                            }
                                        });
                            }
                        });
                    } else {
                        btFoundIt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.clear();
                                mMap.addMarker(markerOptions);
                                VolleySingleton.getInstance(getContext()).updateUserAdventure(idUser, idUserAdventure, true,
                                        new Consumer<UserAdventure>() {
                                            @Override
                                            public void accept(UserAdventure userAdventure) {
                                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                                listener.onFindedTreasure(userAdventure);
                                            }
                                        });
                            }
                        });

                    }
                    if (mustMove) {
                        moveCameraOnUser(mLocation);
                        mustMove = false;
                    }
                }
            });
        }
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) // grant the access from user when the activity created
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, // if the permission wasn't granted so ask for permission
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mLocation = location;
                moveCameraOnUser(location);
                Location treasureLocation = new Location("");
                treasureLocation.setLongitude(treasure.getLongTreasure());
                treasureLocation.setLatitude(treasure.getLatTreasure());
                double distance = location.distanceTo(treasureLocation);
                if (distance < DISTANCE_USER_BETWEEN_TREASURE) {
                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(TIME_VIBRATION);
                    v.cancel();
                    Button btFoundIt = view.findViewById(R.id.btFoundIt);
                    btFoundIt.setVisibility(View.VISIBLE);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mLocation = location;
                        moveCameraOnUser(location);
                    }
                }
            });
            boolean gpsActivated = false;
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, MIN_DISTANCE, locationListener);
                gpsActivated = true;
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, MIN_DISTANCE, locationListener);
                gpsActivated = true;
            }
            if (!gpsActivated) {
                Toast.makeText(getContext(), getString(R.string.geolocalisation_desactivee), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void moveCameraOnUser(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_ZOOM);
        if (mMap != null) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            mMap.moveCamera(yourLocation);
        } else {
            mustMove = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(getContext(), getString(R.string.gps_non_activee), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public interface SearchTreasureListener {

        void onFindedTreasure(UserAdventure userAdventure);

        void onFinishAdventureGoRate();

        void onFinishedAdventureCantRate();
    }
}
