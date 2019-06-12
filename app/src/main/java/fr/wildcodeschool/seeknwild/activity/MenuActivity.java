package fr.wildcodeschool.seeknwild.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.fragment.AdventureChooseFragment;
import fr.wildcodeschool.seeknwild.fragment.AdventureEditFragment;
import fr.wildcodeschool.seeknwild.fragment.GalleryFragment;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        switch (menuItem.getItemId()) {
            case R.id.choisi_une_aventures:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdventureChooseFragment()).commit();
                break;
            case R.id.edite_tes_aventures:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdventureEditFragment()).commit();
                break;
            case R.id.voir_ta_galerie:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GalleryFragment()).commit();
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
}
