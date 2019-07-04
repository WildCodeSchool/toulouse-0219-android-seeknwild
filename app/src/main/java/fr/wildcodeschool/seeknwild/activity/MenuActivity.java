package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.Random;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.fragment.AdventureChooseFragment;
import fr.wildcodeschool.seeknwild.fragment.AdventureEditFragment;
import fr.wildcodeschool.seeknwild.fragment.GalleryFragment;
import fr.wildcodeschool.seeknwild.model.User;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdventureChooseFragment.AdventureChooseListener, AdventureEditFragment.AdventureEditListener {
    private DrawerLayout drawer;
    private TextView tv;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    Fragment mChooseAdventure;
    Fragment mEditAdventure;
    Fragment mActive;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long idUser = user.getIdUser();

        Button btSignOut = findViewById(R.id.btn_sing_out);
        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent goToSignIn = new Intent(MenuActivity.this, SignInActivity.class);
                startActivity(goToSignIn);
            }
        });

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
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mChooseAdventure = new AdventureChooseFragment();
        mEditAdventure = new AdventureEditFragment();
        mActive = mChooseAdventure;

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mChooseAdventure);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.choisiUneAventure);

    }

    public void sayHello(View header) {
        final String hello[] = {getString(R.string.bonjour), getString(R.string.hallo), getString(R.string.hello), getString(R.string.hola), getString(R.string.buongiorno), getString(R.string.ola), getString(R.string.kaixo), getString(R.string.alo)};
        Random r = new Random();
        String motAleatoire = hello[r.nextInt(hello.length)];
        TextView textView = header.findViewById(R.id.hello);
        textView.setText(motAleatoire);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction;
        switch (menuItem.getItemId()) {
            case R.id.choisi_une_aventures:
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mChooseAdventure);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.choisiUneAventure);

                break;
            case R.id.edite_tes_aventures:

                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mEditAdventure);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.editeTesAventures);
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
            super.onBackPressed();
        }
    }

    @Override
    public void onClicked() {

    }
}
