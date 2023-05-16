package com.zubaid.cpd;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }else if(id == R.id.nav_gallery){
                    startActivity(new Intent(getApplicationContext(), Activity_activtiy.class));
                }else if(id == R.id.nav_slideshow){
                    startActivity(new Intent(getApplicationContext(), reflection.class));
                } else if(id == R.id.nav_tools){
                    startActivity(new Intent(getApplicationContext(), Attachment_activity.class));

                } else if(id == R.id.nav_share){
                    startActivity(new Intent(getApplicationContext(), Development_activity.class));

                } else if(id == R.id.nav_send){
                    startActivity(new Intent(getApplicationContext(), Relevent_links_activity.class));

                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}