package fr.wildcodeschool.seeknwild.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Consumer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.Treasure;

public class TreasureAdventureMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int REQUEST_IMAGE_CAPTURE = 1234;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1034;
    private static final int MIN_DISTANCE = 10;
    private static final int DEFAULT_ZOOM = 17;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Double lat;
    private Double lng;
    private Uri mFileUri = null;
    private Long idAdventure;

    //permet de modifier l'action du bouton retour android
    @Override
    public void onBackPressed() {
        Intent intentCreateAdv = new Intent(TreasureAdventureMapsActivity.this, CreateAdventureActivity.class);
        intentCreateAdv.putExtra("idAdventure", idAdventure);
        startActivity(intentCreateAdv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teasure_adventure_maps);

        Intent intent = getIntent();
        idAdventure = intent.getLongExtra("idAdventure", 0);
        final int sizeTreasure = intent.getIntExtra("sizeTreasure", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        askLocationPermission();
        ImageView ivLogo = findViewById(R.id.ivTreasure);
        String url = "https://i.goopics.net/5DbkX.jpg";
        Glide.with(this).load(url).into(ivLogo);
        actionFloattingButton();

        final EditText description = findViewById(R.id.etDescriptionTreasure);
        final Button btCreateTresure = findViewById(R.id.btCreateTreasure);
        btCreateTresure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lat != null && lng != null && !description.getText().toString().isEmpty()) {
                    Treasure treasure = new Treasure();
                    treasure.setDescription(description.getText().toString());
                    treasure.setLongTreasure(lng);
                    treasure.setLatTreasure(lat);
                    treasure.setPictureTreasure("pic");
                    VolleySingleton.getInstance(getApplicationContext()).createTreasure(treasure, idAdventure, new Consumer<Treasure>() {
                        @Override
                        public void accept(Treasure treasure) {
                            if (sizeTreasure == 4) {
                                VolleySingleton.getInstance(getApplicationContext()).publishedAdventure(idAdventure, new VolleySingleton.ResponseListener<Adventure>() {
                                    @Override
                                    public void finished(Adventure adventure) {
                                        //TODO rediriger l'utilisateur vers sa liste d'aventure
                                        Intent intentList = new Intent(TreasureAdventureMapsActivity.this, HomeActivity.class);
                                        startActivity(intentList);
                                    }
                                });
                            } else {
                                Intent intent = new Intent(TreasureAdventureMapsActivity.this, TreasureAdventureMapsActivity.class);
                                intent.putExtra("idAdventure", idAdventure);
                                intent.putExtra("sizeTreasure", sizeTreasure + 1);
                                mMap.clear();
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TreasureAdventureMapsActivity.this);
                    builder.setTitle("");
                    builder.setMessage(getString(R.string.errorEmptyTreasure));
                    builder.setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        if (sizeTreasure == 4) {
            btCreateTresure.setText(R.string.publier);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgFileName, ".jpg", storageDir);
        return image;
    }

    private void dispatchTakePictureIntent() {
        // ouvrir l'application de prise de photo
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // lors de la validation de la photo
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // créer le fichier contenant la photo
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                // TODO : gérer l'erreur
            }

            if (photoFile != null) {
                // récupèrer le chemin de la photo
                mFileUri = FileProvider.getUriForFile(this,
                        "fr.wildcodeschool.seeknwild.fileprovider",
                        photoFile);
                // déclenche l'appel de onActivityResult
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView ivRecupPic = findViewById(R.id.ivPic);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ivRecupPic.setImageURI(mFileUri);
        }
    }

    private void actionFloattingButton() {
        FloatingActionButton floatBtTakePicTreasure = findViewById(R.id.fbTakePicAdventure);
        floatBtTakePicTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

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
                    Toast.makeText(TreasureAdventureMapsActivity.this, getString(R.string.gps_non_activee), Toast.LENGTH_LONG).show();
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
                TreasureAdventureMapsActivity.this, R.raw.stylemap));
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
    }
}