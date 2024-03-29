package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Consumer;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.fragment.AdventureCreateFragment;
import fr.wildcodeschool.seeknwild.fragment.AdventureCreatedListFragment;
import fr.wildcodeschool.seeknwild.fragment.AdventureDescriptionFragment;
import fr.wildcodeschool.seeknwild.fragment.AdventureListFragment;
import fr.wildcodeschool.seeknwild.fragment.CannotRateFragment;
import fr.wildcodeschool.seeknwild.fragment.GalleryFragment;
import fr.wildcodeschool.seeknwild.fragment.RateFragment;
import fr.wildcodeschool.seeknwild.fragment.SearchTreasureFragment;
import fr.wildcodeschool.seeknwild.fragment.TreasureCreateFragment;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdventureListFragment.AdventureChooseListener, AdventureCreatedListFragment.AdventureEditListener,
        AdventureDescriptionFragment.AdventureDescriptionListener, AdventureCreateFragment.CreateAdventureListener,
        TreasureCreateFragment.TreasureCreateListener, SearchTreasureFragment.SearchTreasureListener,
        RateFragment.RateFragmentListener, CannotRateFragment.CannotRateFragmentListener {

    private static final int REQUEST_ADVENTURE_PICTURE = 1234;
    private static final int REQUEST_TREASURE_PICTURE = 1235;
    private AdventureListFragment mChooseAdventure;
    private AdventureCreatedListFragment mEditAdventure;
    private AdventureCreateFragment mCreateAdventure;
    private TreasureCreateFragment mTreasureCreateFragment;
    private AdventureListFragment mAdventureListFragment;
    private SearchTreasureFragment mSearchTreasureFragment;
    private AdventureDescriptionFragment mAdvnetureDescriptionFragment;
    private RateFragment mRateFragment;
    private CannotRateFragment mCannotRateFragment;
    private Fragment mActive;
    private FragmentManager mFragmentManager;
    private DrawerLayout drawer;
    private TextView tv;
    private FirebaseAuth mAuth;
    private Uri mFileUri = null;
    private Button btnPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final String pseudo = user.getPseudo();
        Button btSignOut = findViewById(R.id.btn_sing_out);
        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent goToSignIn = new Intent(MenuActivity.this, SignInActivity.class);
                startActivity(goToSignIn);
            }
        });

        btnPass = findViewById(R.id.btnPass);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.seekn_wild);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View header = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                if (newState == DrawerLayout.STATE_SETTLING) {
                    sayHello(header);
                    TextView name = findViewById(R.id.tvName);
                    name.setText(pseudo);
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        showAdventureList();
    }

    public void showAdventureList() {
        mChooseAdventure = new AdventureListFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mChooseAdventure);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.choisiUneAventure);
        mActive = mChooseAdventure;
    }

    public void sayHello(View header) {
        final String[] hello = {getString(R.string.bonjour), getString(R.string.hallo), getString(R.string.hello), getString(R.string.hola), getString(R.string.buongiorno), getString(R.string.ola), getString(R.string.kaixo), getString(R.string.alo)};
        Random r = new Random();
        String motAleatoire = hello[r.nextInt(hello.length)];
        TextView textView = header.findViewById(R.id.hello);
        textView.setText(motAleatoire);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        btnPass.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction;
        switch (menuItem.getItemId()) {
            case R.id.choisi_une_aventures:
                showAdventureList();

                break;
            case R.id.edite_tes_aventures:
                mEditAdventure = new AdventureCreatedListFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mEditAdventure);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.editeTesAventures);
                mActive = mEditAdventure;
                break;
            case R.id.voir_ta_galerie:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GalleryFragment()).commit();
                getSupportActionBar().setTitle(R.string.voirTaGalerie);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            btnPass.setVisibility(View.GONE);
            if (mActive != mChooseAdventure) {
                showAdventureList();
            }
            //TODO/ mettre un else avec une allertDialog.
        }
    }

    @Override
    public void onAdventureChoosed(Adventure adventure) {
        mAdvnetureDescriptionFragment = new AdventureDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idAdventure", adventure.getIdAdventure());
        mAdvnetureDescriptionFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mAdvnetureDescriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(adventure.getTitle());
        btnPass.setVisibility(View.GONE);
        mActive = mAdvnetureDescriptionFragment;
    }

    @Override
    public void onAdventureEdited(Adventure adventure) {
        mCreateAdventure = new AdventureCreateFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idAdventure", adventure.getIdAdventure());
        mCreateAdventure.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mCreateAdventure);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.creeUneAventure);
        btnPass.setVisibility(View.GONE);
        mActive = mCreateAdventure;
    }

    @Override
    public void onAdventureCreate() {
        mCreateAdventure = new AdventureCreateFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idAdventure", -1);
        mCreateAdventure.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mCreateAdventure);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.creeUneAventure);
        btnPass.setVisibility(View.GONE);
        mActive = mCreateAdventure;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imgFileName, ".jpg", storageDir);
    }

    private void dispatchTakePictureIntent(int request) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                // TODO : gérer l'erreur
            }
            if (photoFile != null) {
                mFileUri = FileProvider.getUriForFile(this,
                        "fr.wildcodeschool.seeknwild.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePictureIntent, request);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADVENTURE_PICTURE && resultCode == RESULT_OK) {
            mCreateAdventure.onPictureLoaded(mFileUri);
        }
        if (requestCode == REQUEST_TREASURE_PICTURE && resultCode == RESULT_OK) {
            mTreasureCreateFragment.onPictureLoaded(mFileUri);
        }
    }

    @Override
    public void onTakeAdventurePicture() {
        dispatchTakePictureIntent(REQUEST_ADVENTURE_PICTURE);
    }

    @Override
    public void onTakeTreasurePicture() {
        dispatchTakePictureIntent(REQUEST_TREASURE_PICTURE);
    }

    @Override
    public void onTreasureCreated(Long idAdventure, int sizeTreasure) {
        mTreasureCreateFragment = new TreasureCreateFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idAdventure", idAdventure);
        bundle.putInt("sizeTreasure", sizeTreasure);
        mTreasureCreateFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mTreasureCreateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.creaTresasure);
        btnPass.setVisibility(View.GONE);
        mActive = mTreasureCreateFragment;
    }

    @Override
    public void onPublishedAdventure(Adventure adventure) {
        mAdventureListFragment = new AdventureListFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mAdventureListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.creeUneAventure);
        btnPass.setVisibility(View.GONE);
        mActive = mAdventureListFragment;
    }


    @Override
    public void onCreateTreasure(Adventure adventure) {
        mTreasureCreateFragment = new TreasureCreateFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idAdventure", adventure.getIdAdventure());
        bundle.putInt("sizeTreasure", adventure.getTreasures().size());
        mTreasureCreateFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mTreasureCreateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.creaTresasure);
        btnPass.setVisibility(View.GONE);
        mActive = mTreasureCreateFragment;
    }

    @Override
    public void onAdventureDescriptionChosen(final UserAdventure userAdventure) {
        mSearchTreasureFragment = new SearchTreasureFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mSearchTreasureFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(String.format("%s%s/5", getString(R.string.ttr), String.valueOf(userAdventure.getCurrentTreasure())));
        btnPass.setVisibility(View.VISIBLE);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(getApplicationContext()).updateUserAdventure(
                        userAdventure.getUser().getIdUser(), userAdventure.getIdUserAdventure(), false,
                        new Consumer<UserAdventure>() {
                            @Override
                            public void accept(UserAdventure userAdventure) {
                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                if (userAdventure.getCurrentTreasure() >= 5) {
                                    onFinishAdventureGoRate();
                                } else {
                                    onSkippedTreasure(userAdventure);
                                }
                            }
                        });
            }
        });
        mActive = mSearchTreasureFragment;
    }

    @Override
    public void onFindedTreasure(final UserAdventure userAdventure) {
        mSearchTreasureFragment = new SearchTreasureFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("userAdventure", userAdventure.getCurrentTreasure());
        mSearchTreasureFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mSearchTreasureFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(String.format("%s%s/5", getString(R.string.ttr), String.valueOf(userAdventure.getCurrentTreasure())));
        btnPass.setVisibility(View.VISIBLE);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(getApplicationContext()).updateUserAdventure(
                        userAdventure.getUser().getIdUser(), userAdventure.getIdUserAdventure(), false,
                        new Consumer<UserAdventure>() {
                            @Override
                            public void accept(UserAdventure userAdventure) {
                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                if (userAdventure.getCurrentTreasure() >= 5) {
                                    onFinishAdventureGoRate();
                                } else {
                                    onSkippedTreasure(userAdventure);
                                }
                            }
                        });
            }
        });
        mActive = mSearchTreasureFragment;
    }

    public void onSkippedTreasure(UserAdventure userAdventure) {
        mSearchTreasureFragment = new SearchTreasureFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("userAdventure", userAdventure.getCurrentTreasure());
        mSearchTreasureFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mSearchTreasureFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(String.format("%s%s/5", getString(R.string.ttr), String.valueOf(userAdventure.getCurrentTreasure())));
        btnPass.setVisibility(View.VISIBLE);
        mActive = mSearchTreasureFragment;
    }

    @Override
    public void onFinishAdventureGoRate() {
        // TODO : finaliser la notation
        /*mRateFragment = new RateFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mRateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.treasurec);*/

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mChooseAdventure);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.choisiUneAventure);
        btnPass.setVisibility(View.GONE);
        mActive = mChooseAdventure;
    }

    @Override
    public void onFinishedAdventureCantRate() {
        mCannotRateFragment = new CannotRateFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mCannotRateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.treasurec);
        btnPass.setVisibility(View.GONE);
        mActive = mCannotRateFragment;
    }

    @Override
    public void onRatedAdventure() {
        mAdventureListFragment = new AdventureListFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mAdventureListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.treasurec);
        btnPass.setVisibility(View.GONE);
        mActive = mAdventureListFragment;
    }

    @Override
    public void onNotRatedAdventure() {
        mAdventureListFragment = new AdventureListFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mAdventureListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.treasurec);
        btnPass.setVisibility(View.GONE);
        mActive = mAdventureListFragment;
    }
}
