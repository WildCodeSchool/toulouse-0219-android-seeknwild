package fr.wildcodeschool.seeknwild.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.Treasure;

public class TreasureCreateFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1034;
    private static final int MIN_DISTANCE = 10;
    private static final int DEFAULT_ZOOM = 17;

    private SupportMapFragment mapFragment;
    private TreasureCreateFragment.TreasureCreateListener listener;
    private View view;
    private Long idAdventure;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Double lat;
    private Double lng;
    private Uri mFileUri = null;
    private boolean mustMove = false;
    private Location mLocation;

    public TreasureCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (TreasureCreateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_teasure_adventure_maps, container, false);

        idAdventure = this.getArguments().getLong("idAdventure");
        final int sizeTreasure = this.getArguments().getInt("sizeTreasure");

        FloatingActionButton floatBtTakePicTreasure = view.findViewById(R.id.fbTakePicAdventure);
        floatBtTakePicTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTakeTreasurePicture();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.stylemap));
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Création du marqueur
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tresor1));
                            markerOptions.position(latLng);
                            // ajoute un titre au marqueur
                            markerOptions.title(getString(R.string.le_tresor));
                            // effacer le marqueur précédent
                            mMap.clear();
                            // Zommer sur le marqueur placé
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            // Ajoute le marqueur à la carte
                            mMap.addMarker(markerOptions);
                            // récupére la position GPS du marqueur
                            lat = markerOptions.getPosition().latitude;
                            lng = markerOptions.getPosition().longitude;
                        }
                    });
                    if (mustMove) {
                        moveCameraOnUser(mLocation);
                        mustMove = false;
                    }
                }
            });
        }
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        final EditText description = view.findViewById(R.id.etDescriptionTreasure);
        final Button btCreateTresure = view.findViewById(R.id.btCreateTreasure);
        btCreateTresure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (lat != null && lng != null && !description.getText().toString().isEmpty() && mFileUri != null) {

                    Treasure treasure = new Treasure();
                    treasure.setDescription(description.getText().toString());
                    treasure.setLongTreasure(lng);
                    treasure.setLatTreasure(lat);
                    treasure.setPictureTreasure("");

                    VolleySingleton.getInstance(getContext()).createTreasure(treasure, idAdventure, new Consumer<Treasure>() {
                        @Override
                        public void accept(final Treasure treasure) {
                            try {
                                final Long idTreasure = treasure.getIdTreasure();
                                VolleySingleton.getInstance(getContext()).uploadTreasurePicture(mFileUri, "treasure-" + idTreasure + ".jpg", idTreasure, new Consumer<String>() {
                                    @Override
                                    public void accept(String filePath) {
                                        if (filePath == null) {
                                            //TODO Afficher un message d'erreur
                                            Toast.makeText(getContext(), getString(R.string.take_treasure_picture), Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (sizeTreasure >= 4) {
                                                VolleySingleton.getInstance(getContext()).publishedAdventure(idAdventure, new Consumer<Adventure>() {
                                                    @Override
                                                    public void accept(Adventure adventure) {
                                                        adventure.setPublished(true);
                                                        listener.onPublishedAdventure(adventure);
                                                    }
                                                });
                                            } else {
                                                mMap.clear();
                                                listener.onTreasureCreated(idAdventure, sizeTreasure + 1);
                                            }
                                        }
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("");
                    builder.setMessage(getString(R.string.errorEmptyTreasure));
                    builder.setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        if (sizeTreasure >= 4) {
            btCreateTresure.setText(R.string.publier);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) // grant the access from user when the activity created
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, // if the permission wasn't granted so ask for permission
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else { // if it was granted so get the location
            getLocation();
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mLocation = location;
                moveCameraOnUser(location);
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
        }
        else {
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

    public void onPictureLoaded(Uri fileUri) {
        mFileUri = fileUri;

        ImageView ivRecupPic = view.findViewById(R.id.ivPic);
        ivRecupPic.setImageURI(mFileUri);
    }

    public interface TreasureCreateListener {

        void onTakeTreasurePicture();

        void onTreasureCreated(Long idAdventure, int sizeTreasure);

        void onPublishedAdventure(Adventure adventure);
    }
}
