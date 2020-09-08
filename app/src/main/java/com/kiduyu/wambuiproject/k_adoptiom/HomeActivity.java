package com.kiduyu.wambuiproject.k_adoptiom;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView txtActiontitle;
    CircleImageView circleImageView;
    RelativeLayout relativeLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        relativeLayout = findViewById(R.id.layoutid);

        txtActiontitle = findViewById(R.id.txt_actiontitle);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void callFragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack("adds").commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    public void ClickNavigation(View view) {

        Fragment fragment;
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressedd();
                break;
            case R.id.lvl_home:
                txtActiontitle.setText("Home");
                fragment = new HomeFragment();
                callFragment(fragment);

                break;
            case R.id.myprofile:
                txtActiontitle.setText("Profile");
                fragment = new ProfileFragment();
                callFragment(fragment);
                break;
            case R.id.agencies:
                txtActiontitle.setText("Adoption Agencies");
                fragment = new AgenciesFragment();
                callFragment(fragment);
                break;
            case R.id.favourites:
                txtActiontitle.setText("My Favourites");
                fragment = new FavouritesFragment();
                callFragment(fragment);
                break;
            case R.id.appointments:
                txtActiontitle.setText("My Appointments");
                fragment = new AppointmentsFragment();
                callFragment(fragment);

                break;
            case R.id.payments:
                txtActiontitle.setText("Payments done");
                fragment = new PaymentsFragment();
                callFragment(fragment);
                break;
            case R.id.share:
                shareApp();

                break;
            case R.id.logout:

                break;

            case R.id.img_noti:
                //startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void onBackPressedd() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}