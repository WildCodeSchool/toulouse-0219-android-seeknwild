package fr.wildcodeschool.seeknwild.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Consumer;
import android.view.View;
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
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class SearchTreasureActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1034;
    private static final int MIN_DISTANCE = 1;
    private static final int DEFAULT_ZOOM = 17;
    private static final String IMAGE_TEST = "https://i.goopics.net/5DbkX.jpg";
    private static final int RANDOM_HEADING = 360;
    private static final int RANDOM_DISTANCE = 250;
    private static final int RADIUS_RANDOM_CIRCLE = 250;
    private static final int DISTANCE_USER_BETWEEN_TREASURE = 500;
    private static final int TIME_VIBRATION = 1500;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Treasure treasure;
    private UserAdventure userAdventure;
    private Long idUserAdventure;
    private Long idUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_treasure);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        idUser = user.getIdUser();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        idUserAdventure = userAdventureSingleton.getUserAdventureId();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasure = treasures.get(userAdventure.getCurrentTreasure());

        TextView etDescriptionTreasure = findViewById(R.id.etDescriptionTreasure);
        etDescriptionTreasure.setText(treasure.getDescription());

        ImageView treasureImg = findViewById(R.id.ivTreasure);
        Glide.with(this).load(treasure.getPictureTreasure()).into(treasureImg);

        //TODO: Photo du trésor associé.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        askLocationPermission();
        //ImageView ivLogo = findViewById(R.id.ivTreasure);
        //Glide.with(this).load(IMAGE_TEST).into(ivLogo);
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            getLocation();
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
                    Toast.makeText(SearchTreasureActivity.this, getString(R.string.gps_non_activee), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void moveCameraOnUser(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_ZOOM);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.moveCamera(yourLocation);
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                moveCameraOnUser(location);
                //TODO : récupérer la distance entre le marqueur et la position de l'utilisateur
                Location newLocation = new Location("newLocation");
                newLocation.setLatitude(treasure.getLatTreasure());
                newLocation.setLongitude(treasure.getLongTreasure());
                double distance = location.distanceTo(newLocation);
                if (distance < DISTANCE_USER_BETWEEN_TREASURE) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(TIME_VIBRATION);
                    v.cancel();
                    Button btFoundIt = findViewById(R.id.btFoundIt);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        moveCameraOnUser(location);
                    }
                }
            });
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, MIN_DISTANCE, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, MIN_DISTANCE, locationListener);
            } else {
                Toast.makeText(this, getString(R.string.geolocalisation_desactivee), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                SearchTreasureActivity.this, R.raw.stylemap));
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
                .strokeColor(Color.LTGRAY)
                .fillColor(Color.LTGRAY));

        final Button btFoundIt = findViewById(R.id.btFoundIt);
        btFoundIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                mMap.addMarker(markerOptions);
                VolleySingleton.getInstance(getApplicationContext()).updateUserAdventure(idUser, idUserAdventure, true,
                        new Consumer<UserAdventure>() {
                            @Override
                            public void accept(UserAdventure userAdventure) {
                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                Intent intent = new Intent(SearchTreasureActivity.this, TakePictureActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}